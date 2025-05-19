package manfrinmarco.map;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.EnemyFactory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemFactory;
import manfrinmarco.items.ItemType;

/**
 * Carica la definizione di mappa da un file JSON.
 * Il JSON deve avere un array "rooms" dove ogni stanza è:
 * {
 *   "id": "entrata",
 *   "name": "Entrata",
 *   "description": "Una stanza buia...",
 *   "exits": { "north": "armeria", ... },
 *   "items": [ {"id":"PozioneCurativa","type":"POTION","power":0}, ... ],
 *   "enemy": { "type": "goblin", "drop": { "id":"Chiave","type":"KEY" } }
 * }
 */
public class MapLoader {
    public static CompositeRoom load(String jsonFile) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(jsonFile));
        JsonNode roomsNode = root.get("rooms");
        // prima pass: crea tutte le stanze
        Map<String, Room> registry = new HashMap<>();
        Iterator<JsonNode> it = roomsNode.elements();
        while (it.hasNext()) {
            JsonNode r = it.next();
            String id = r.get("id").asText();
            String name = r.get("name").asText();
            String desc = r.get("description").asText();
            boolean composite = r.has("subRooms");
            Room room = composite ? new CompositeRoom(name, desc) : new Room(name, desc);
            registry.put(id, room);
        }
        // seconda pass: imposta uscite, items, nemici e composizione
        it = roomsNode.elements();
        while (it.hasNext()) {
            JsonNode r = it.next();
            String id = r.get("id").asText();
            Room room = registry.get(id);
            // exits
            JsonNode exits = r.get("exits");
            if (exits != null) {
                exits.fields().forEachRemaining(field -> {
                    Direction dir = Direction.valueOf(field.getKey().toUpperCase());
                    Room target = registry.get(field.getValue().asText());
                    if (target != null) room.setExit(dir, target);
                });
            }
            // items
            JsonNode items = r.get("items");
            if (items != null) {
                items.forEach(itemNode -> {
                    String iid = itemNode.has("id") ? itemNode.get("id").asText() : null;
                    Item item;
                    if (iid != null) {
                        item = ItemFactory.create(iid);
                    } else {
                        String name = itemNode.get("name").asText();
                        ItemType type = ItemType.valueOf(itemNode.get("type").asText());
                        int power = itemNode.has("power") ? itemNode.get("power").asInt() : 0;
                        item = new Item(name, type, power);
                    }
                    room.addItem(item);
                });
            }
            // enemy
            JsonNode e = r.get("enemy");
            if (e != null) {
                String etype = e.get("type").asText();
                Enemy enemy = EnemyFactory.createEnemy(etype);
                JsonNode drop = e.get("drop");
                if (drop != null && drop.has("id")) {
                    enemy.setDrop(ItemFactory.create(drop.get("id").asText()));
                }
                room.setEnemy(enemy);
            }
            // composite
            if (room instanceof CompositeRoom && r.has("subRooms")) {
                r.get("subRooms").forEach(sr -> ((CompositeRoom) room).addRoom(registry.get(sr.asText())));
                if (r.has("mainRoom")) {
                    ((CompositeRoom) room).setMainRoom(registry.get(r.get("mainRoom").asText()));
                }
            }
        }
        // assume il root composite ha id "root"
        return (CompositeRoom) registry.get(root.get("root").asText());
    }
}
