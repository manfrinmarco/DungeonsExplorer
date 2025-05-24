package manfrinmarco.map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.EnemyFactory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemFactory;
import manfrinmarco.items.ItemType;
import manfrinmarco.security.GameException;

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
@Deprecated
 public class MapLoaderOld {
    private static final Logger log = Logger.getLogger(MapLoader.class.getName());

    public static CompositeRoom load(String jsonFile) {
        try {
            log.log(Level.INFO, "MapLoader: loading map from {0}", jsonFile);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(jsonFile));
            JsonNode roomsNode = root.get("rooms");
            log.fine("MapLoader: first pass - creating rooms");
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
                log.log(Level.FINE, "MapLoader: created room id={0} name={1}", new Object[]{id, name});
            }
            log.fine("MapLoader: second pass - configuring rooms");
            // seconda pass: imposta uscite, items, nemici e composizione
            it = roomsNode.elements();
            while (it.hasNext()) {
                JsonNode r = it.next();
                String id = r.get("id").asText();
                Room room = registry.get(id);
                log.log(Level.FINE, "MapLoader: configuring room id={0}", id);
                // exits
                JsonNode exits = r.get("exits");
                if (exits != null) {
                    exits.fields().forEachRemaining(field -> {
                        Direction dir = Direction.valueOf(field.getKey().toUpperCase());
                        Room target = registry.get(field.getValue().asText());
                        if (target != null) room.setExit(dir, target);
                    });
                    log.log(Level.FINE, "MapLoader: exits set for room id={0}", id);
                }
                // items
                JsonNode items = r.get("items");
                if (items != null) {
                    items.forEach(itemNode -> {
                        String iid = itemNode.has("id") ? itemNode.get("id").asText() : null;
                        Item item;
                        if (iid != null) {
                            item = ItemFactory.createItem(iid);
                        } else {
                            String name = itemNode.get("name").asText();
                            ItemType type = ItemType.valueOf(itemNode.get("type").asText());
                            int power = itemNode.has("power") ? itemNode.get("power").asInt() : 0;
                            item = new Item(name, type, power);
                        }
                        room.addItem(item);
                        log.log(Level.FINE, "MapLoader: added item {0} to room id={1}", new Object[]{item.getName(), id});
                    });
                }
                // enemy
                JsonNode e = r.get("enemy");
                if (e != null) {
                    String etype = e.get("type").asText();
                    Enemy enemy = EnemyFactory.createEnemy(etype);
                    JsonNode drop = e.get("drop");
                    if (drop != null && drop.has("id")) {
                        enemy.setDrop(ItemFactory.createItem(drop.get("id").asText()));
                    }
                    room.setEnemy(enemy);
                    log.log(Level.FINE, "MapLoader: added enemy {0} to room id={1}", new Object[]{etype, id});
                }
                // composite
                if (room instanceof CompositeRoom && r.has("subRooms")) {
                    r.get("subRooms").forEach(sr -> ((CompositeRoom) room).addRoom(registry.get(sr.asText())));
                    if (r.has("mainRoom")) {
                        ((CompositeRoom) room).setMainRoom(registry.get(r.get("mainRoom").asText()));
                    }
                    log.log(Level.FINE, "MapLoader: configured CompositeRoom id={0} with subRooms {1}", new Object[]{id, r.get("subRooms")});
                }
            }
            log.log(Level.INFO, "MapLoader: finished loading map root={0}", root.get("root").asText());
            // assume il root composite ha id "root"
            return (CompositeRoom) registry.get(root.get("root").asText());
        } catch (IOException e) {
            log.log(Level.SEVERE, "MapLoader I/O error loading map file " + jsonFile, e);
            throw new GameException("Errore I/O durante il caricamento della mappa: " + e.getMessage());
        } catch (Exception e) {
            log.log(Level.SEVERE, "MapLoader unexpected error", e);
            throw new GameException("Errore parsing mappa: " + e.getMessage());
        }
    }
}
