package manfrinmarco.core;

import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;

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
            case "stato" -> {
                System.out.println("HP: " + context.getPlayer().getHealth());
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
}