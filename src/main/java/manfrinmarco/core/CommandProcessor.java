package manfrinmarco.core;

import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.Player;
import manfrinmarco.events.GameEvent;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;
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
            // case "salva" -> manfrinmarco.io.GameFileManager.saveContext(context);
            // case "carica" -> {
            //     GameContext loaded = manfrinmarco.io.GameFileManager.loadContext();
            //     if (loaded != null) {
            //         context.copyFrom(loaded);
            //         System.out.println("Partita caricata.");
            //         lookAround();
            //     }
            // }
            case "esplora" -> exploreRooms();
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
        }
    }

    private void moveTo(String dirString) {
        try {
            Direction dir = Direction.valueOf(dirString.toUpperCase());
            Room current = context.getCurrentRoom();
            Room next = current.getExit(dir);
            if (next != null) {
                context.setCurrentRoom(next);
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
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (item.getType() == ItemType.POTION) {
                    context.getPlayer().heal(20);
                    System.out.println("Hai usato " + item.getName() + " e recuperato 20 HP.");
                    inventory.removeItem(item);
                    return;
                }
            }
        }
        System.out.println("Oggetto non trovato o non utilizzabile.");
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
        System.out.println("Stanze collegate:");
        Room current = context.getCurrentRoom();
        for (Direction dir : Direction.values()) {
            Room adjacent = current.getExit(dir);
            if (adjacent != null) {
                System.out.println("- " + dir.name().toLowerCase() + ": " + adjacent.getName());
            }
        }
    }
}