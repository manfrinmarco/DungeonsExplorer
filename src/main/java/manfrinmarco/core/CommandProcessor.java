package manfrinmarco.core;

import java.util.Arrays;
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
import manfrinmarco.map.RoomIterator;
import manfrinmarco.security.GameException;

public class CommandProcessor extends AbstractCommandProcessor {
    private final GameContext context = GameContext.getInstance();
    private static final Logger log = Logger.getLogger(CommandProcessor.class.getName());

    @Override
    protected void executeCommand(String[] tokens, String line) {
        if (tokens.length == 0) return;
        String command = tokens[0].toLowerCase();
        log.log(Level.FINE, "Esecuzione comando: {0}", command);

        switch (command) {
            case "look" -> lookAround();
            case "status" -> showStatus();
            case "i" -> showInventory();
            case "explore" -> exploreRooms();
            case "go" -> {
                if (tokens.length < 2) {
                    System.out.println("Dove vuoi andare?");
                } else {
                    moveTo(tokens[1]);
                }
            }
            case "attack" -> {
                Enemy enemy = context.getCurrentRoom().getEnemy();
                if (enemy == null || !enemy.isAlive()) {
                    System.out.println("Non c'è nessun nemico da attaccare.");
                    return;
                }
                attack();
            }
            // TODO: gli oggetti possono avere un nome con più parole
            case "use" -> {
                if (tokens.length < 2) {
                    System.out.println("Specifica l'oggetto da usare.");
                } else {
                    //useItem(tokens[1]);
                    String itemName = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
                    useItem(itemName);
                }
            }
            case "equip" -> {
                if (tokens.length < 2) {
                    System.out.println("Specifica cosa vuoi equipaggiare.");
                } else {
                    //equipItem(tokens[1]);
                    String itemName = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
                    equipItem(itemName);
                }
            }
            case "take" -> {
                if (tokens.length < 2) {
                    System.out.println("Specifica cosa vuoi prendere.");
                } else {
                    //pickItem(tokens[1]);
                    String itemName = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
                    pickItem(itemName);
                }
            }
            
            case "combine" -> {
                // Esempio comando: combine pozione rossa + erba magica = pozione super
                String input = line.substring("combine".length()).trim();
                
                if (!input.contains("+") || !input.contains("=")) {
                    System.out.println("Sintassi non valida. Usa: combine oggetto1 + oggetto2 = nuovoOggetto");
                    break;
                }

                try {
                    String[] parts = input.split("=");
                    String[] items = parts[0].split("\\+");

                    String name1 = items[0].trim();
                    String name2 = items[1].trim();
                    String name3 = parts[1].trim();

                    combineItems(name1, name2, name3);
                } catch (Exception e) {
                    System.out.println("Errore nel parsing del comando combine. Controlla la sintassi.");
                    log.log(Level.WARNING, "Errore parsing combine: " + input, e);
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
        for (Direction dir : Direction.values()) {
            if (room.getExit(dir) != null) {
                System.out.println("C'è un passaggio a " + dir.name().toLowerCase());
            }
        }
        if (room.getEnemy() != null && room.getEnemy().isAlive()) {
            System.out.println("Un nemico è qui: " + room.getEnemy().getName());
            if (room.getEnemy().getStrategy() instanceof manfrinmarco.entities.AggressiveStrategy) {
                System.out.println("Il " + room.getEnemy().getName() + " ti coglie di sorpresa!");
                room.getEnemy().executeStrategy(context.getPlayer());
                System.out.println("Hai subito un attacco! HP attuali: " + context.getPlayer().getHealth());
            }
        }
        if (!room.getItems().isEmpty()) {
            System.out.println("Oggetti nella stanza:");
            for (Item item : room.getItems()) {
                System.out.println("- " + item.getName());
            }
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
            System.out.println("Il " + enemy.getName() + " ti attacca! HP rimanenti: " + player.getHealth());
            if (!player.isAlive()) {
                System.out.println("Sei morto! Game Over.");
                System.exit(0);
            }
        } else {
            System.out.println("Hai sconfitto il " + enemy.getName() + "!");
            context.getEventManager().notify(new GameEvent("enemy_defeated", "Hai sconfitto un nemico!", enemy));
        }
    }

    private void showStatus() {
        Player player = context.getPlayer();
        System.out.println("HP: " + player.getHealth());
    }

    private void showInventory() {
        System.out.println("Inventario:");
        context.getPlayer().getInventory().stream()
            .map(Item::getName)
            .forEach(name -> System.out.println("- " + name));
    }

    private void useItem(String itemName) {
        Inventory inventory = context.getPlayer().getInventory();
        inventory.stream()
            .filter(item -> item.getName().equalsIgnoreCase(itemName.trim()))
            .findFirst()
            .ifPresentOrElse(item -> {
                switch (item.getType()) {
                    case WEAPON -> {
                        System.out.println("Non puoi usare un'arma in questo modo. Usa 'equip' per equipaggiarla.");
                    }
                    case ARMOR -> {
                        System.out.println("Non puoi usare un'armatura in questo modo. Usa 'equip' per equipaggiarla.");
                    }
                    case POTION -> {
                        int healAmount = 20;
                        try {
                            healAmount = Integer.parseInt(manfrinmarco.config.GameConfig.get("item.potion.healAmount"));
                        } catch (NumberFormatException e) {
                            System.err.println("Valore di healAmount non valido, uso 20 di default.");
                        }
                        context.getPlayer().heal(healAmount);
                        System.out.println("Hai usato " + item.getName() + " e recuperato " + healAmount + " HP.");
                        inventory.removeItem(item);
                    }
                    case KEY -> {
                        Room room = context.getCurrentRoom();
                        boolean unlocked = false;
                        for (Direction dir : Direction.values()) {
                            Room adjacent = room.getExit(dir);
                            if (adjacent != null && adjacent.isLocked() && adjacent.unlock(item)) {
                                System.out.println("Hai sbloccato la stanza a " + dir.name().toLowerCase() + ".");
                                inventory.removeItem(item);
                                unlocked = true;
                                break;
                            }
                        }
                        if (!unlocked) {
                            System.out.println("Nessuna porta è stata sbloccata.");
                        }
                    }
                    default -> {
                        System.out.println("Hai usato " + item.getName() + ".");
                        inventory.removeItem(item);
                    }
                }
            }, () -> {
                log.log(Level.INFO, "Oggetto non trovato in inventario: {0}", itemName);
                System.out.println("Oggetto non trovato.");
            });
    }

    private void equipItem(String itemName) {
        Inventory inventory = context.getPlayer().getInventory();
        inventory.stream()
            .filter(item -> item.getName().equalsIgnoreCase(itemName))
            .findFirst()
            .ifPresentOrElse(item -> {
                if (item.getType() != ItemType.WEAPON && item.getType() != ItemType.ARMOR) {
                    System.out.println("Non puoi equipaggiare questo oggetto.");
                } else {
                    context.getPlayer().equip(item);
                }
            }, () -> System.out.println("Oggetto non trovato o non equipaggiabile."));
    }

    private void pickItem(String itemName) {
        Room room = context.getCurrentRoom();
        room.getItems().stream()
            .filter(item -> item.getName().equalsIgnoreCase(itemName))
            .findFirst()
            .ifPresentOrElse(item -> {
                context.getPlayer().getInventory().addItem(item);
                room.removeItem(item);
                System.out.println("Hai raccolto: " + item.getName());
            }, () -> {
                log.log(Level.INFO, "Tentativo di prendere oggetto non presente: {0}", itemName);
                System.out.println("Oggetto non trovato nella stanza.");
            });
    }

    private void exploreRooms() {
        Room current = context.getCurrentRoom();

        // Se ci troviamo nella stanza principale di una CompositeRoom
        if (current instanceof CompositeRoom composite) {
            System.out.println("Stanze interne di " + composite.getName() + ":");
            RoomIterator iterator = new RoomIterator(composite.getSubRooms());
            while (iterator.hasNext()) {
                Room room = iterator.next();
                System.out.println("- " + room.getName());
            }
        } else {
            // Proviamo a vedere se la stanza corrente appartiene a una CompositeRoom
            CompositeRoom parent = null;
            if (current != null) {
                parent = current.getSuperRoom();
            }
            if (parent != null) {
                System.out.println("Stanze interne di " + parent.getName() + ":");
                RoomIterator iterator = new RoomIterator(parent.getSubRooms());
                while (iterator.hasNext()) {
                    Room room = iterator.next();
                    System.out.println("- " + room.getName());
                }
            } else {
                System.out.println("Stanze collegate:");
                if (current != null) {
                    for (Direction dir : Direction.values()) {
                        Room adjacent = current.getExit(dir);
                        if (adjacent != null) {
                            System.out.println("- " + dir.name().toLowerCase() + ": " + adjacent.getName());
                        }
                    }
                } else {
                    System.out.println("Errore: stanza corrente non trovata.");
                }
            }
        }
    }
    
    private void combineItems(String name1, String name2, String name3) {
        Inventory inventory = context.getPlayer().getInventory();
        Item item1 = inventory.stream()
            .filter(item -> item.getName().equalsIgnoreCase(name1))
            .findFirst().orElse(null);
        Item item2 = inventory.stream()
            .filter(item -> item.getName().equalsIgnoreCase(name2))
            .findFirst().orElse(null);

        if (item1 == null || item2 == null) {
            System.out.println("Uno o entrambi gli oggetti non sono nell'inventario.");
            return;
        }

        CompositeItem combinedObj = new CompositeItem(name3);
        combinedObj.addItem(item1);
        combinedObj.addItem(item2);
        combinedObj.setPower(item1.getPower() + item2.getPower());

        inventory.removeItem(item1);
        inventory.removeItem(item2);
        inventory.addItem(combinedObj);

        log.log(Level.FINE, "Oggetti combinati: {0} + {1}", new Object[]{name1, name2});
        System.out.println("Hai creato un oggetto combinato: " + combinedObj.getName());
    }
}