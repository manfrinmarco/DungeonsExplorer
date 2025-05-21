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

import manfrinmarco.entities.DefensiveStrategy;
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
public class MapLoader {
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
                String id = r.has("id") ? r.get("id").asText() : "-1";
                String type = r.has("type") ? r.get("type").asText() : null;
                String name = r.has("name") ? r.get("name").asText() : r.get("type").asText();
                String desc = r.has("description") ? r.get("description").asText() : "Non vedi nulla.";
                boolean composite = r.has("subRooms");
                Room room;
                if (composite) {
                    room = new CompositeRoom(name, desc);
                } else if (type!=null) {
                    room = RoomFactory.createRoom(type);
                } else {
                    room = new Room(name, desc);
                }
                registry.put(id, room);
                log.log(Level.FINE, "MapLoader: created room id={0} name={1}", new Object[]{type, name});
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
                        String itype = itemNode.has("type") ? itemNode.get("type").asText() : "Undef";
                        Item item;
                        if (!itype.equals("Undef")) {
                            item = ItemFactory.create(itype);
                        } else {
                            String name = itemNode.has("name") ? itemNode.get("name").asText() : "Undef";
                            ItemType type = ItemType.valueOf(itemNode.has("type") ? itemNode.get("type").asText() : "Undef");
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
                    Enemy enemy;
                    if (e.has("type")) {
                        enemy = EnemyFactory.createEnemy(e.get("type").asText());
                    } else {
                        String ename = e.has("name") ? e.get("name").asText() : "Nemico";
                        int strength = e.has("strength") ? e.get("strength").asInt() : 10;
                        enemy = new Enemy(ename, strength, new DefensiveStrategy());
                    }
                    JsonNode drop = e.get("drop");
                    if (drop != null && drop.has("id")) {
                        enemy.setDrop((Item)ItemFactory.create(drop.get("id").asText()));
                    }
                    room.setEnemy(enemy);
                    log.log(Level.FINE, "MapLoader: added enemy {0} to room id={1}", new Object[]{enemy.getName(), id});
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
