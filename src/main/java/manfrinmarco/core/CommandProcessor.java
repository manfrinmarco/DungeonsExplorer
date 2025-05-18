package manfrinmarco.core;

import manfrinmarco.security.GameException;

public abstract class CommandProcessor {
    public static void process(String input) throws GameException {
        switch (input) {
            case "exit":
                GameContext.getInstance().endGame();
                System.out.println("Hai lasciato il dungeon.");
                break;
            default:
                throw new GameException("Comando sconosciuto: " + input, "Comando non riconosciuto. Riprova.");
        }
    }
}