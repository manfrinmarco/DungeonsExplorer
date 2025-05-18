//annotations/autoLaod.java
package manfrinmarco.annotations;

public class AutoLoad {
    
}

//config/Config.java
package manfrinmarco.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameConfig {
    private static final Properties config = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("game.properties")) {
            config.load(fis);
            System.out.println("Configurazione di gioco caricata.");
        } catch (IOException e) {
            System.err.println("Errore nel caricamento della configurazione: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return config.getProperty(key);
    }
}
//core/CommandProcessor.java
package manfrinmarco.core;

import manfrinmarco.io.InputSanitizer;

public abstract class CommandProcessor {

    public final void processCommand(String input) {
        String sanitized = sanitizeInput(input);
        String[] tokens = parseCommand(sanitized);
        executeCommand(tokens);
    }

    protected String sanitizeInput(String input) {
        return InputSanitizer.clean(input);
    }

    protected String[] parseCommand(String sanitizedInput) {
        return sanitizedInput.trim().split("\\s+");
    }

    protected void executeCommand(String[] tokens) {
        if (tokens.length == 0) {
            System.out.println("Comando vuoto.");
            return;
        }
        switch (tokens[0].toLowerCase()) {
            case "exit":
                manfrinmarco.core.GameContext.getInstance().endGame();
                System.out.println("Chiusura del gioco...");
                break;
            default:
                System.out.println("Comando sconosciuto: " + tokens[0]);
        }
    }
}
//core/Game.java
package manfrinmarco.core;

import java.util.Scanner;

public class Game {
    private final Scanner scanner = new Scanner(System.in);
    private final GameContext context = GameContext.getInstance();
    private final CommandProcessor processor = new CommandProcessor();

    public void start() {
        GameLogger.getLogger().info("Gioco avviato.");
        System.out.println("Benvenuto in Dungeon Explorer!");

        while (!context.isGameOver()) {
            System.out.print("> ");
            String input = scanner.nextLine();
            processor.processCommand(input);
        }

        System.out.println("Hai concluso il gioco!");
        GameLogger.getLogger().info("Gioco terminato.");
    }
}
//core/gameContext.java
package manfrinmarco.core;

public class GameContext {
    private static GameContext instance;
    private boolean gameOver = false;

    private GameContext() {}

    public static GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void endGame() {
        this.gameOver = true;
    }

    // Aggiungeremo qui anche Player, Mappa, Inventory, ecc.
}
//core/GameLogger.java
package manfrinmarco.core;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GameLogger {
    private static final Logger logger = Logger.getLogger("DungeonExplorerLogger");

    static {
        try {
            FileHandler fileHandler = new FileHandler("dungeon.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Errore nel setup del logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
//core/gamestatememento.java
package manfrinmarco.core;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GameLogger {
    private static final Logger logger = Logger.getLogger("DungeonExplorerLogger");

    static {
        try {
            FileHandler fileHandler = new FileHandler("dungeon.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Errore nel setup del logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
//entities/basicEnemyStrategy.java
package manfrinmarco.entities;

/**
 * Basic enemy strategy: the enemy attacks with fixed power.
 */
public class BasicEnemyStrategy implements EnemyStrategy {

    /**
     * Basic enemy strategy: the enemy attacks with fixed power.
     */
    @Override
    public void execute(Enemy enemy) {
        System.out.println(enemy.getName() + " attacca con forza " + enemy.getAttackPower() + "!");
        // Può essere esteso con logica di targeting o danno reale
    }
}
//entities/enemy.java
package manfrinmarco.entities;

public class Enemy extends Entity {
    private EnemyStrategy strategy;

    public Enemy(String name, int health, int attackPower, EnemyStrategy strategy) {
        super(name, health, attackPower);
        this.strategy = strategy;
    }

    @Override
    public void takeTurn() {
        if (strategy != null) {
            strategy.execute(this);
        }
    }

    public EnemyStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(EnemyStrategy strategy) {
        this.strategy = strategy;
    }
}
//entities/enemyFactory.java
package manfrinmarco.entities;
public class EnemyFactory {

    public static Enemy createEnemy(String type) {
        EnemyStrategy strategy = new BasicEnemyStrategy(); // Placeholder strategy

        switch (type.toLowerCase()) {
            case "goblin":
                return new Enemy("Goblin", 30, 5, strategy);
            case "orc":
                return new Enemy("Orc", 50, 10, strategy);
            case "skeleton":
                return new Enemy("Skeleton", 40, 7, strategy);
            default:
                throw new IllegalArgumentException("Tipo nemico sconosciuto: " + type);
        }
    }
}
//entities/enemyStrategy.java
package manfrinmarco.entities;

public interface EnemyStrategy {
    void execute(Enemy enemy);
}
//entities/entity.java
package manfrinmarco.entities;

public abstract class Entity {
    protected String name;
    protected int health;
    protected int attackPower;

    public Entity(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public abstract void takeTurn();
}
//entites/player.java
package manfrinmarco.entities;

import manfrinmarco.items.Inventory;

public class Player extends Entity {
    private Inventory inventory;
    private String currentRoomId;

    public Player(String name, int health, int attackPower) {
        super(name, health, attackPower);
        this.inventory = new Inventory();
        this.currentRoomId = "start"; // default value
    }

    @Override
    public void takeTurn() {
        // Il turno del giocatore viene gestito dall'input del giocatore stesso.
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getCurrentRoomId() {
        return currentRoomId;
    }

    public void setCurrentRoomId(String currentRoomId) {
        this.currentRoomId = currentRoomId;
    }
}
//events/eventListener.java
package manfrinmarco.events;

public class EventListener {
    
}
//events/eventManager.java
package manfrinmarco.events;

public class EventManager {
    
}
//events/gameEvent.java
package manfrinmarco.events;

public class GameEvent {
    
}
//io/gamefilemanager.java
package manfrinmarco.io;

public class GameFileManager {
    
}
//io/inputSanitizer.java
package manfrinmarco.io;

import java.util.Scanner;

public class InputSanitizer {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readInput() {
        String input = scanner.nextLine();
        return input.trim().toLowerCase();
    }
}

//io/reflectionLoaders.java
package manfrinmarco.io;

public class ReflectionLoader {
    
}
//items/compositeitem.java
package manfrinmarco.items;

import java.util.ArrayList;
import java.util.List;

public class CompositeItem extends Item {
    private final List<Item> components;

    public CompositeItem(String name, String description, ItemType type) {
        super(name, description, type);
        this.components = new ArrayList<>();
    }

    public void addComponent(Item item) {
        components.add(item);
    }

    public void removeComponent(Item item) {
        components.remove(item);
    }

    public List<Item> getComponents() {
        return new ArrayList<>(components);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" [Componenti: ");
        for (Item item : components) {
            sb.append(item.getName()).append(", ");
        }
        if (!components.isEmpty()) {
            sb.setLength(sb.length() - 2); // remove last comma
        }
        sb.append("]");
        return sb.toString();
    }
}
//items/inventory.java
package manfrinmarco.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory implements Iterable<Item> {
    private final List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
//items/item.java
package manfrinmarco.items;

public class Item {
    private final String name;
    private final String description;
    private final ItemType type;

    public Item(String name, String description, ItemType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " (" + type + "): " + description;
    }
}
//item/itembuilder.java
package manfrinmarco.items;

public class ItemBuilder {
    private String name;
    private String description;
    private ItemType type;

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemBuilder setType(ItemType type) {
        this.type = type;
        return this;
    }

    public Item build() {
        if (name == null || description == null || type == null) {
            throw new IllegalStateException("Tutti i campi devono essere impostati per costruire un Item.");
        }
        return new Item(name, description, type);
    }
}
//items/itemtype.java
package manfrinmarco.items;

public enum ItemType {
    WEAPON,
    ARMOR,
    POTION,
    KEY,
    MISC
}
//map/compositeRoom.java
package manfrinmarco.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompositeRoom extends Room {
    private final List<Room> subRooms;

    public CompositeRoom(String id, String description) {
        super(id, description);
        this.subRooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        subRooms.add(room);
    }

    public List<Room> getSubRooms() {
        return Collections.unmodifiableList(subRooms);
    }
}
//map/direction.java
package manfrinmarco.map;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    UP,
    DOWN
}
//map/room.java
package manfrinmarco.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private final String id;
    private final String description;
    private final Map<Direction, String> exits;

    public Room(String id, String description) {
        this.id = id;
        this.description = description;
        this.exits = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Map<Direction, String> getExits() {
        return Collections.unmodifiableMap(exits);
    }

    public void setExit(Direction direction, String targetRoomId) {
        exits.put(direction, targetRoomId);
    }

    public String getExit(Direction direction) {
        return exits.get(direction);
    }
}
//map/roomFactory.java
package manfrinmarco.map;

import java.util.Map;

public class RoomFactory {

    public static Room createBasicRoom(String id, String description) {
        return new Room(id, description);
    }

    public static Room createRoomWithExits(String id, String description, Map<Direction, String> exits) {
        Room room = new Room(id, description);
        for (Map.Entry<Direction, String> entry : exits.entrySet()) {
            room.setExit(entry.getKey(), entry.getValue());
        }
        return room;
    }
}
//map/roomiterator.java
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RoomIterator implements Iterator<Room> {
    private final List<Room> rooms;
    private int currentIndex;

    public RoomIterator(List<Room> rooms) {
        this.rooms = rooms;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < rooms.size();
    }

    @Override
    public Room next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Nessun'altra stanza disponibile.");
        }
        return rooms.get(currentIndex++);
    }
//security/gameexception.java
package manfrinmarco.security;

public class GameException extends Exception {
    private final String userMessage;

    public GameException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
//strategy/aggressiveStrategy.java
package manfrinmarco.strategy;

public class AggressiveStrategy {
    
}
//strategy/combactStrategy.java
package manfrinmarco.strategy;

public class CombatStrategy {
    
}
//main.java
package manfrinmarco;

public class Main {
    public static void main(String[] args) {
        try {
            // Inizializza configurazione di gioco
            manfrinmarco.config.GameConfig.load();

            // Avvia il gioco
            manfrinmarco.core.Game game = new manfrinmarco.core.Game();
            game.start();
        } catch (Exception e) {
            manfrinmarco.core.GameLogger.getLogger().severe("Errore critico: " + e.getMessage());
        }
    }
}
