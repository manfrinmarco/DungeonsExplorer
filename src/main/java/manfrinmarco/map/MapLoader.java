package manfrinmarco.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

public class MapLoader {
    private static final Logger log = Logger.getLogger(MapLoader.class.getName());

    public static CompositeRoom load(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Room> rooms = new HashMap<>();
        Map<String, Item> itemRegistry = new HashMap<>();
        Map<String, Enemy> enemyRegistry = new HashMap<>();
        Map<String, CompositeRoom> compositeRooms = new HashMap<>();
        String startPointId = null;

        try {
            JsonNode root = mapper.readTree(new File("maps/"+fileName));

            if (root.has("startPoint")) {
                startPointId = root.get("startPoint").asText();
            }

            // Load items
            if (root.has("items")) {
                for (JsonNode itemNode : root.get("items")) {
                    String id = itemNode.get("id").asText();
                    Item item;
                    if (itemNode.has("type")) {
                        item = ItemFactory.createItem(itemNode.get("type").asText());
                        // possible feature to add
                        //if (itemNode.has("name")) item.setName(itemNode.get("name").asText());
                        //if (itemNode.has("health")) item.setPower(itemNode.get("health").asInt());
                        log.log(Level.INFO, "Item creato con factory: {0}", item.getName());
                    } else {
                        String name = itemNode.get("name").asText();
                        ItemType itemType = ItemType.valueOf(itemNode.get("ItemType").asText());
                        int power = itemNode.has("health") ? itemNode.get("health").asInt() : 0;
                        item = new Item(name, itemType, power);
                        log.log(Level.INFO, "Item creato con costruttore: {0}", item.getName());
                    }
                    itemRegistry.put(id, item);
                }
            }

            // Load enemies
            if (root.has("enemyes")) {
                for (JsonNode enemyNode : root.get("enemyes")) {
                    String id = enemyNode.get("id").asText();
                    Enemy enemy;
                    // Create enemy:
                    if (enemyNode.has("type")) {
                        enemy = EnemyFactory.createEnemy(enemyNode.get("type").asText());
                        // possible feature to add
                        // if (enemyNode.has("name") && !enemyNode.get("name").asText().equalsIgnoreCase(enemy.getName())) {
                        //     enemy.setName(enemyNode.get("name").asText());
                        // }
                        // if (enemyNode.has("health")) {
                        //     enemy.setHealth(enemyNode.get("health").asInt());
                        // }
                        log.log(Level.INFO, "Nemico creato con factory: {0}", enemy.getName());
                    } else {
                        String name = enemyNode.get("name").asText();
                        int health = enemyNode.get("health").asInt();
                        enemy = new Enemy(name, health, new DefensiveStrategy());
                        log.log(Level.INFO, "Enemy creato con costruttore: {0}", name);
                    }
                    // Add enemy drop if present
                    if (enemyNode.has("drop")) {
                        String dropId = enemyNode.get("drop").asText();
                        Item drop = itemRegistry.get(dropId);
                        if (drop != null) enemy.setDrop(drop);
                    }
                    enemyRegistry.put(id, enemy);
                }
            }

            // Load rooms
            if (root.has("rooms")) {
                for (JsonNode roomNode : root.get("rooms")) {
                    String id = roomNode.get("id").asText();
                    Room room;
                    
                    // Create room
                    if (roomNode.has("type")) {
                        room = RoomFactory.createRoom(roomNode.get("type").asText());
                    } else {
                        String name = roomNode.get("name").asText();
                        String description = roomNode.get("description").asText();
                        room = new Room(name, description);
                        log.log(Level.INFO, "Room creata con costruttore: {0}", name);
                    }

                    // Uscite -> se lo faccio gia dopo qui è inutile
                    // if (roomNode.has("exits")) {
                    //     Iterator<Map.Entry<String, JsonNode>> fields = roomNode.get("exits").fields();
                    //     while (fields.hasNext()) {
                    //         Map.Entry<String, JsonNode> entry = fields.next();
                    //         room.setExit(Direction.valueOf(entry.getKey().toUpperCase()), null); // lo colleghiamo dopo
                    //     }
                    // }

                    // Oggetti
                    if (roomNode.has("items")) {
                        for (JsonNode itemRef : roomNode.get("items")) {
                            if (!itemRef.has("id")) {
                                log.log(Level.SEVERE, "Oggetto stanza privo di campo ''id'': {0}", itemRef.toString());
                                throw new GameException("Oggetto stanza privo di campo 'id': " + itemRef.toString());
                            }
                            String itemId = itemRef.get("id").asText();
                            Item item = itemRegistry.get(itemId);
                            if (item == null) {
                                throw new GameException("Item non trovato: " + itemId);
                            }
                            room.addItem(item);
                        }
                    }

                    // Nemici
                    if (roomNode.has("enemy")) {
                        JsonNode enemyData = roomNode.get("enemy");
                        if (enemyData.isArray()) {
                            for (JsonNode e : enemyData) {
                                Enemy enemy = enemyRegistry.get(e.get("id").asText());
                                if (enemy != null) room.setEnemy(enemy); // puoi estendere a lista di nemici se serve
                            }
                        } else {
                            Enemy enemy = enemyRegistry.get(enemyData.get("id").asText());
                            if (enemy != null) room.setEnemy(enemy);
                        }
                    }

                    // Chiave richiesta
                    if (roomNode.has("keyRequired")) {
                        String keyId = roomNode.get("keyRequired").get("id").asText();
                        Item key = itemRegistry.get(keyId);
                        if (key != null) room.setLocked(true, key);
                    }

                    rooms.put(id, room);
                }
            }

            // Collega le uscite
            for (JsonNode roomNode : root.get("rooms")) {
                String id = roomNode.get("id").asText();
                Room room = rooms.get(id);
                if (roomNode.has("exits")) {
                    Iterator<Map.Entry<String, JsonNode>> fields = roomNode.get("exits").fields();
                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> entry = fields.next();
                        Direction dir = Direction.valueOf(entry.getKey().toUpperCase());
                        Room exitRoom = rooms.get(entry.getValue().asText());
                        room.setExit(dir, exitRoom);
                    }
                }
            }

            // Composite room
            if (root.has("compositeRoom")) {
                for (JsonNode node : root.get("compositeRoom")) {
                    String id = node.get("id").asText();
                    String name = node.get("name").asText();
                    String description = node.get("description").asText();
                    List<Room> subRooms = new ArrayList<>();
                    for (JsonNode sub : node.get("subRooms")) {
                        Room r = rooms.get(sub.asText());
                        if (r != null) subRooms.add(r);
                    }
                    Room main = rooms.get(node.get("mainRoom").asText());
                    CompositeRoom compRoom = new CompositeRoom(name, description);
                    for (Room room : subRooms) {
                        compRoom.addRoom(room);
                    }
                    compRoom.setMainRoom(main);
                    compositeRooms.put(id, compRoom);
                    log.log(Level.INFO, "CompositeRoom caricata: {0}", name);
                }
            }

            if (startPointId == null || !compositeRooms.containsKey(startPointId)) {
                throw new GameException("StartPoint mancante o non trovato: " + startPointId);
            }

            log.log(Level.INFO, "Mappa caricata con successo. StartPoint: {0}", startPointId);
            return compositeRooms.get(startPointId);


        } catch (IOException e) {
            log.log(Level.SEVERE, "Errore lettura file mappa: {0}", e.getMessage());
            throw new GameException("Errore durante il caricamento della mappa.");
        }
    }
}