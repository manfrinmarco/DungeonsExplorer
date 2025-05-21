package manfrinmarco.map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
public class MapLoader {
    private static final Logger log = Logger.getLogger(MapLoader.class.getName());

    public static CompositeRoom load(String jsonFile) {
        try {
            log.log(Level.INFO, "MapLoader: loading map from {0}", jsonFile);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(jsonFile));
            JsonNode roomsNode = root.get("rooms");

            Map<String, Room> registry = new HashMap<>();

            // Fase 1: crea tutte le stanze
            for (JsonNode r : roomsNode) {
                String id = r.get("id").asText();
                String name = r.get("name").asText();
                String description = r.get("description").asText();
                boolean isComposite = r.path("composite").asBoolean(false);
                Room room = isComposite ? new CompositeRoom(name, description) : new Room(name, description);
                registry.put(id, room);
                log.fine(() -> "MapLoader: created room id=" + id + " name=" + name);
            }

            // Fase 2: configura stanze
            for (JsonNode r : roomsNode) {
                String id = r.get("id").asText();
                Room room = registry.get(id);

                // Uscite
                JsonNode exits = r.get("exits");
                if (exits != null) {
                    exits.fields().forEachRemaining(field -> {
                        Direction dir = Direction.valueOf(field.getKey().toUpperCase());
                        Room target = registry.get(field.getValue().asText());
                        if (target != null) room.setExit(dir, target);
                    });
                }

                // Oggetti
                JsonNode items = r.get("items");
                if (items != null) {
                    for (JsonNode itemNode : items) {
                        String iid = itemNode.path("id").asText(null);
                        Item item = iid != null ? ItemFactory.createItem(iid) :
                            new Item(
                                itemNode.get("name").asText(),
                                ItemType.valueOf(itemNode.get("type").asText()),
                                itemNode.path("power").asInt(0)
                            );
                        room.addItem(item);
                    }
                }

                // Nemici
                JsonNode enemyNode = r.get("enemy");
                if (enemyNode != null && enemyNode.has("type")) {
                    Enemy enemy = EnemyFactory.createEnemy(enemyNode.get("type").asText());
                    JsonNode drop = enemyNode.get("drop");
                    if (drop != null && drop.has("id")) {
                        enemy.setDrop(ItemFactory.createItem(drop.get("id").asText()));
                    }
                    room.setEnemy(enemy);
                }

                // Composite room
                if (room instanceof CompositeRoom) {
                    JsonNode subRooms = r.get("subRooms");
                    if (subRooms != null) {
                        for (JsonNode sr : subRooms) {
                            ((CompositeRoom) room).addRoom(registry.get(sr.asText()));
                        }
                    }
                    JsonNode mainRoom = r.get("mainRoom");
                    if (mainRoom != null) {
                        ((CompositeRoom) room).setMainRoom(registry.get(mainRoom.asText()));
                    }
                }
            }

            String rootId = root.get("root").asText();
            log.info(() -> "MapLoader: finished loading map root=" + rootId);
            return (CompositeRoom) registry.get(rootId);
        } catch (IOException e) {
            log.log(Level.SEVERE, "MapLoader I/O error loading map file " + jsonFile, e);
            throw new GameException("Errore I/O durante il caricamento della mappa: " + e.getMessage());
        } catch (Exception e) {
            log.log(Level.SEVERE, "MapLoader unexpected error", e);
            throw new GameException("Errore parsing mappa: " + e.getMessage());
        }
    }
}
