package manfrinmarco.core;

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

public class CommandProcessor extends AbstractCommandProcessor {
    private final GameContext context = GameContext.getInstance();

    @Override
    protected void executeCommand(String[] tokens) {
        if (tokens.length == 0) return;
        String command = tokens[0].toLowerCase();

        switch (command) {
            case "guarda" -> lookAround();
            case "muovi" -> {
                if (tokens.length < 2) {
                    System.out.println("Dove vuoi andare?");
                } else {
                    moveTo(tokens[1]);
                }
            }
            case "attacca" -> attack();
            case "stato" -> showStatus();
            case "inventario" -> showInventory();
            case "usa" -> {
                if (tokens.length < 2) {
                    System.out.println("Specifica l'oggetto da usare.");
                } else {
                    useItem(tokens[1]);
                }
            }
            case "equip" -> {
                if (tokens.length < 2) {
                    System.out.println("Specifica cosa vuoi equipaggiare.");
                } else {
                    equipItem(tokens[1]);
                }
            }
            case "prendi" -> {
                if (tokens.length < 2) {
                    System.out.println("Specifica cosa vuoi prendere.");
                } else {
                    pickItem(tokens[1]);
                }
            }
            case "salva" -> {
                GameStateMemento snapshot = new GameStateMemento(context);
                GameFileManager.saveMemento(snapshot);
            }
            case "carica" -> {
                GameStateMemento loaded = GameFileManager.loadMemento();
                if (loaded != null) {
                    context.copyFrom(loaded.getSnapshot());
                    // Ripristina il EventManager e i suoi listener
                    GameContext.getInstance().setEventManager(new EventManager());
                    GameContext.getInstance().getEventManager().subscribe(new ScoreListener());
                    System.out.println("Partita caricata.");
                    lookAround();
                }
            }
            case "esplora" -> exploreRooms();
            case "combina" -> {
                if (tokens.length < 3) {
                    System.out.println("Specifica due oggetti da combinare.");
                } else {
                    combineItems(tokens[1], tokens[2]);
                }
}
            default -> System.out.println("Comando sconosciuto.");
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
            if (next != null) {
                context.setCurrentRoom(next);
                Enemy enemy = next.getEnemy();
                if (enemy != null && enemy.isAlive() && enemy.getStrategy() instanceof manfrinmarco.entities.AggressiveStrategy) {
                    System.out.println("Il " + enemy.getName() + " ti coglie di sorpresa!");
                    enemy.executeStrategy(context.getPlayer());
                    System.out.println("Hai subito un attacco! HP attuali: " + context.getPlayer().getHealth());
                    if (!context.getPlayer().isAlive()) {
                        System.out.println("Sei morto! Game Over.");
                        System.exit(0);
                    }
                }
                System.out.println("Ti muovi verso " + dir.name().toLowerCase());
                lookAround();
            } else {
                System.out.println("Non c'è nulla in quella direzione.");
            }
        } catch (IllegalArgumentException e) {
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
            context.getEventManager().notify(new GameEvent("enemy_defeated", "Hai sconfitto un nemico!"));
        }
    }

    private void showStatus() {
        Player player = context.getPlayer();
        System.out.println("HP: " + player.getHealth());
    }

    private void showInventory() {
        System.out.println("Inventario:");
        for (Item item : context.getPlayer().getInventory()) {
            System.out.println("- " + item.getName());
        }
    }

    private void useItem(String itemName) {
        Inventory inventory = context.getPlayer().getInventory();
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName.trim())) {
                if (item.getType() == ItemType.POTION) {
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
                if (item.getType() == ItemType.KEY) {
                    Room room = context.getCurrentRoom();
                    for (Direction dir : Direction.values()) {
                        Room adjacent = room.getExit(dir);
                        if (adjacent != null && adjacent.isLocked() && adjacent.unlock(item)) {
                            System.out.println("Hai sbloccato la stanza a " + dir.name().toLowerCase() + ".");
                            inventory.removeItem(item);
                            return;
                        }
                    }
                    System.out.println("Nessuna porta è stata sbloccata.");
                    return;
                }
                System.out.println("Hai usato " + item.getName() + ".");
                inventory.removeItem(item);
                return;
            }
        }
        System.out.println("Oggetto non trovato.");
    }

    private void equipItem(String itemName) {
        Inventory inventory = context.getPlayer().getInventory();
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                context.getPlayer().equip(item);
                return;
            }
        }
        System.out.println("Oggetto non trovato o non equipaggiabile.");
    }

    private void pickItem(String itemName) {
        Room room = context.getCurrentRoom();
        for (Item item : room.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                context.getPlayer().getInventory().addItem(item);
                room.removeItem(item);
                System.out.println("Hai raccolto: " + item.getName());
                return;
            }
        }
        System.out.println("Oggetto non trovato nella stanza.");
    }

    private void exploreRooms() {
        Room current = context.getCurrentRoom();

        // Se ci troviamo nella stanza principale di una CompositeRoom
        if (current instanceof CompositeRoom composite) {
            System.out.println("Stanze interne di " + composite.getName() + ":");
            for (Room room : composite.getSubRooms()) {
                System.out.println("- " + room.getName());
            }
        } else {
            // Proviamo a vedere se la stanza corrente appartiene a una CompositeRoom
            CompositeRoom parent = findParentComposite(current);
            if (parent != null) {
                System.out.println("Stanze interne di " + parent.getName() + ":");
                for (Room room : parent.getSubRooms()) {
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

    private CompositeRoom findParentComposite(Room room) {
        // Se GameContext contiene una CompositeRoom come mappa principale
        Room parent = GameContext.getInstance().getCurrentRoom();
        if (parent instanceof CompositeRoom composite) {
            for (Room sub : composite.getSubRooms()) {
                if (sub == room) return composite;
            }
        }
        return null;
    }
    
    private void combineItems(String name1, String name2) {
        Inventory inventory = context.getPlayer().getInventory();
        Item item1 = null, item2 = null;

        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name1)) item1 = item;
            else if (item.getName().equalsIgnoreCase(name2)) item2 = item;
        }

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

        System.out.println("Hai creato un oggetto combinato: " + armaCombinata.getName());
    }

}