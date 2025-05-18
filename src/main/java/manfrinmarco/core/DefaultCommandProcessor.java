package manfrinmarco.core;

import manfrinmarco.entities.*;
import manfrinmarco.items.*;
import manfrinmarco.map.*;

public class DefaultCommandProcessor extends CommandProcessor {
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
}