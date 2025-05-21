package manfrinmarco.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.Player;
import manfrinmarco.events.EventManager;
import manfrinmarco.events.GameEvent;
import manfrinmarco.events.ScoreListener;
import manfrinmarco.io.GameFileManager;
import manfrinmarco.items.CompositeItem;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;
import manfrinmarco.map.CompositeRoom;
import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;
import manfrinmarco.security.GameException;

/**
 * Concrete implementation of a command processor that defines how each user command
 * is interpreted and executed. This class uses the Template Method pattern:
 * the method {@code executeCommand(String[] tokens)} is invoked by the superclass
 * to execute the specific command logic, while the general flow is handled by
 * {@code AbstractCommandProcessor}.
 */
public class CommandProcessor extends AbstractCommandProcessor {
    private final GameContext context = GameContext.getInstance();
    private static final Logger log = Logger.getLogger(CommandProcessor.class.getName());

    /**
     * Implements the specific logic for each command recognized by the game.
     * Called by the template method defined in AbstractCommandProcessor.
     * 
     * @param tokens command and arguments parsed from user input
     */
    @Override
    protected void executeCommand(String[] tokens) {
        if (tokens.length == 0) return;
        String command = tokens[0].toLowerCase();
        log.log(Level.FINE, "Esecuzione comando: {0}", command);

        switch (command) {
            case "look" -> lookAround();
            case "go" -> {
                if (tokens.length < 2) {
                    log.warning("Comando muovi senza direzione");
                    System.out.println("Dove vuoi andare?");
                } else {
                    moveTo(tokens[1]);
                }
            }
            case "attack" -> {
                Enemy enemy = context.getCurrentRoom().getEnemy();
                if (enemy == null || !enemy.isAlive()) {
                    log.info("Comando attacca invocato ma nessun nemico presente");
                    System.out.println("Non c'è nessun nemico da attaccare.");
                    return;
                }
                attack();
            }
            case "status" -> showStatus();
            case "i" -> showInventory();
            case "use" -> {
                if (tokens.length < 2) {
                    log.warning("Comando usa invocato senza specificare oggetto");
                    System.out.println("Specifica l'oggetto da usare.");
                } else {
                    useItem(tokens[1]);
                }
            }
            case "equip" -> {
                if (tokens.length < 2) {
                    log.warning("Comando equip invocato senza specificare oggetto");
                    System.out.println("Specifica cosa vuoi equipaggiare.");
                } else {
                    equipItem(tokens[1]);
                }
            }
            case "take" -> {
                if (tokens.length < 2) {
                    log.warning("Comando prendi invocato senza specificare oggetto");
                    System.out.println("Specifica cosa vuoi prendere.");
                } else {
                    pickItem(tokens[1]);
                }
            }
            case "save" -> {
                new Thread(() -> {
                    try {
                        GameStateMemento snapshot = new GameStateMemento(context);
                        GameFileManager.saveMemento(snapshot);
                        System.out.println("Gioco salvato");
                        log.info("Stato di gioco salvato.");
                    } catch (GameException ge) {
                        log.log(Level.INFO, "Errore salvataggio: {0}", ge.getMessage());
                        System.out.println("Errore durante il salvataggio.");
                    }
                }).start();
            }
            case "load" -> {
                new Thread(() -> {
                    GameStateMemento loaded = GameFileManager.loadMemento();
                    if (loaded != null) {
                        try {
                            context.copyFrom(loaded.getSnapshot());
                            GameContext.getInstance().setEventManager(new EventManager());
                            GameContext.getInstance().getEventManager().subscribe(new ScoreListener());
                            System.out.println("Partita caricata.");
                            log.info("Stato di gioco caricato.");
                            lookAround();
                        } catch (GameException ge) {
                            log.log(Level.INFO, "Errore caricamento: {0}", ge.getMessage());
                            System.out.println("Errore durante il caricamento.");
                        }
                    } else {
                        System.out.println("Nessun salvataggio trovato.");
                    }
                }).start();
            }
            case "explore" -> exploreRooms();
            case "combine" -> {
                if (tokens.length < 3) {
                    System.out.println("Specifica due oggetti da combinare.");
                } else {
                    combineItems(tokens[1], tokens[2]);
                }
            }
            default -> {
                log.log(Level.WARNING, "Comando sconosciuto: {0}", command);
                System.out.println("Comando sconosciuto.");
            }
        }
    }

    private void lookAround() {
        Room room = context.getCurrentRoom();
        System.out.println("Ti trovi in: " + room.getName());
        System.out.println(room.getDescription());
        java.util.Arrays.stream(Direction.values())
            .filter(dir -> room.getExit(dir) != null)
            .forEach(dir -> System.out.println("C'è un passaggio a " + dir.name().toLowerCase()));
        if (room.getEnemy() != null && room.getEnemy().isAlive()) {
            System.out.println("C'è un nemico: " + room.getEnemy().getName());
            if (room.getEnemy().getStrategy() instanceof manfrinmarco.entities.AggressiveStrategy) {
                System.out.println(room.getEnemy().getName() + " ti coglie di sorpresa!");
                room.getEnemy().executeStrategy(context.getPlayer());
                System.out.println("Hai subito un attacco! HP attuali: " + context.getPlayer().getHealth());
            }
        }
        if (!room.getItems().isEmpty()) {
            System.out.println("Oggetti nella stanza:");
            room.getItems().forEach(item -> System.out.println("- " + item.getName()));
        }
    }

    private void moveTo(String dirString) {
        try {
            Direction dir = Direction.valueOf(dirString.toUpperCase());
            Room current = context.getCurrentRoom();
            Room next = current.getExit(dir);
            if (next == null) {
                System.out.println("Non c'è nulla in quella direzione.");
                return;
            }
            if (next.isLocked()) {
                System.out.println("La porta verso " + dir.name().toLowerCase() + " è chiusa. Usa una chiave per sbloccarla.");
                return;
            }
            context.setCurrentRoom(next);
            Enemy enemy = next.getEnemy();
            if (enemy != null && enemy.isAlive() && enemy.getStrategy() instanceof manfrinmarco.entities.AggressiveStrategy) {
                System.out.println(enemy.getName() + " ti coglie di sorpresa!");
                enemy.executeStrategy(context.getPlayer());
                System.out.println("Hai subito un attacco! HP attuali: " + context.getPlayer().getHealth());
                if (!context.getPlayer().isAlive()) {
                    System.out.println("Sei morto! Game Over.");
                    System.exit(0);
                }
            }
            System.out.println("Ti muovi verso " + dir.name().toLowerCase());
            lookAround();
        } catch (IllegalArgumentException e) {
            log.log(Level.WARNING, "Direzione non valida: {0}", dirString);
            System.out.println("Direzione non valida.");
        }
    }
    
    private void attack() {
        Enemy enemy = context.getCurrentRoom().getEnemy();
        Player player = context.getPlayer();
        if (enemy == null || !enemy.isAlive()) {
            System.out.println("Non c'è nessun nemico da attaccare.");
            return;
        }
        player.attack(enemy);
        System.out.println("Hai attaccato il " + enemy.getName() + ". HP rimanenti: " + enemy.getHealth());
        if (enemy.isAlive()) {
            enemy.executeStrategy(player);
            System.out.println(enemy.getName() + " ti attacca! HP rimanenti: " + player.getHealth());
            if (!player.isAlive()) {
                System.out.println("Sei morto! Game Over.");
                System.exit(0);
            }
        } else {
            System.out.println("Hai sconfitto " + enemy.getName() + "!");
            context.getEventManager().notify(new GameEvent("enemy_defeated", "Hai sconfitto un nemico!", enemy));
        }
    }

    private void showStatus() {
        Player player = context.getPlayer();
        System.out.println("HP: " + player.getHealth());
    }

    private void showInventory() {
        System.out.println("Inventario:");
        context.getPlayer().getInventory().forEach(item ->
            System.out.println("- " + item.getName()));
    }

    private void useItem(String itemName) {
        Inventory inventory = context.getPlayer().getInventory();
        Item item = inventory.stream()
            .filter(i -> i.getName().equalsIgnoreCase(itemName.trim()))
            .findFirst().orElse(null);
        if (item != null) {
            if (item.getType() == ItemType.WEAPON) {
                System.out.println("Non puoi usare un'arma in questo modo. Usa 'equip' per equipaggiarla.");
                return;
            }
            if (item.getType() == ItemType.CURATIVE_POTION) {
                int healAmount = 20;
                try {
                    healAmount = Integer.parseInt(manfrinmarco.config.GameConfig.get("item.potion.healAmount"));
                } catch (NumberFormatException e) {
                    System.err.println("Valore di healAmount non valido, uso 20 di default.");
                }
                context.getPlayer().heal(healAmount);
                System.out.println("Hai usato " + item.getName() + " e recuperato " + healAmount + " HP.");
                inventory.removeItem(item);
                return;
            }
            if (item.getType() == ItemType.MAGIC_POTION){
                var p = context.getPlayer();
                p.setPower(p.getBaseDamage() + item.getPower());
                System.out.println("con la pozione" + item.getName()+ "la tua forza aumentata temporaneamente!");
                
                new Thread(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ignored) {}
                    p.setPower(p.getBaseDamage() - item.getPower());
                    System.out.println("L'effetto della pozione è svanito.");
    }).start();
            }
            if (item.getType() == ItemType.KEY) {
                Room room = context.getCurrentRoom();
                boolean unlocked = java.util.Arrays.stream(Direction.values())
                    .map(room::getExit)
                    .filter(adjacent -> adjacent != null && adjacent.isLocked() && adjacent.unlock(item))
                    .findFirst().isPresent();
                if (unlocked) {
                    // Find the direction for message
                    Direction unlockedDir = java.util.Arrays.stream(Direction.values())
                        .filter(dir -> {
                            Room adjacent = room.getExit(dir);
                            return adjacent != null && !adjacent.isLocked();
                        })
                        .findFirst().orElse(null);
                    if (unlockedDir != null)
                        System.out.println("Hai sbloccato la stanza a " + unlockedDir.name().toLowerCase() + ".");
                    inventory.removeItem(item);
                    return;
                }
                System.out.println("Nessuna porta è stata sbloccata.");
                return;
            }
            System.out.println("Hai usato " + item.getName() + ".");
            inventory.removeItem(item);
            return;
        }
        log.log(Level.INFO, "Oggetto non trovato in inventario: {0}", itemName);
        System.out.println("Oggetto non trovato.");
    }

    private void equipItem(String itemName) {
        Inventory inventory = context.getPlayer().getInventory();
        Item item = inventory.stream()
            .filter(i -> i.getName().equalsIgnoreCase(itemName) && i.getType() == ItemType.WEAPON)
            .findFirst().orElse(null);
        if (item != null) {
            context.getPlayer().equip(item);
        } else {
            System.out.println("Oggetto non trovato o non equipaggiabile.");
        }
    }

    private void pickItem(String itemName) {
        Room room = context.getCurrentRoom();
        Item item = room.getItems().stream()
            .filter(i -> i.getName().equalsIgnoreCase(itemName))
            .findFirst().orElse(null);
        if (item != null) {
            context.getPlayer().getInventory().addItem(item);
            room.removeItem(item);
            System.out.println("Hai raccolto: " + item.getName());
            return;
        }
        log.log(Level.INFO, "Tentativo di prendere oggetto non presente: {0}", itemName);
        System.out.println("Oggetto non trovato nella stanza.");
    }

    private void exploreRooms() {
        Room current = context.getCurrentRoom();

        if (current instanceof CompositeRoom composite) {
            System.out.println("Stanze interne di " + composite.getName() + ":");
            composite.getSubRooms().forEach(r -> System.out.println("- " + r.getName()));
        } else {
            CompositeRoom parent = findParentComposite(current);
            if (parent != null) {
                System.out.println("Stanze interne di " + parent.getName() + ":");
                parent.getSubRooms().forEach(r -> System.out.println("- " + r.getName()));
            } else {
                System.out.println("Stanze collegate:");
                if (current != null) {
                    java.util.Arrays.stream(Direction.values())
                        .map(dir -> new Object[]{dir, current.getExit(dir)})
                        .filter(arr -> arr[1] != null)
                        .forEach(arr -> System.out.println("- " + ((Direction)arr[0]).name().toLowerCase() + ": " + ((Room)arr[1]).getName()));
                } else {
                    System.out.println("Errore: stanza corrente non trovata.");
                }
            }
        }
    }
    
    private CompositeRoom findParentComposite(Room room) {
        return GameContext.getInstance().getAllRooms().values().stream()
            .filter(candidate -> candidate instanceof CompositeRoom)
            .map(candidate -> (CompositeRoom) candidate)
            .filter(composite -> composite.getSubRooms().stream().anyMatch(sub -> sub == room))
            .findFirst().orElse(null);
    }

    private void combineItems(String name1, String name2) {
        Inventory inventory = context.getPlayer().getInventory();
        Item item1 = inventory.stream()
            .filter(i -> i.getName().equalsIgnoreCase(name1))
            .findFirst().orElse(null);
        Item item2 = inventory.stream()
            .filter(i -> i.getName().equalsIgnoreCase(name2))
            .findFirst().orElse(null);

        if (item1 == null || item2 == null) {
            System.out.println("Uno o entrambi gli oggetti non sono nell'inventario.");
            return;
        }

        CompositeItem armaCombinata = new CompositeItem("Arma Combinata");
        armaCombinata.addItem(item1);
        armaCombinata.addItem(item2);
        armaCombinata.setPower(item1.getPower() + item2.getPower());

        inventory.removeItem(item1);
        inventory.removeItem(item2);
        inventory.addItem(armaCombinata);

        log.log(Level.FINE, "Oggetti combinati: {0} + {1}", new Object[]{name1, name2});
        System.out.println("Hai creato un oggetto combinato: " + armaCombinata.getName());
    }

}