package manfrinmarco.core;

import manfrinmarco.io.InputSanitizer;
import manfrinmarco.security.GameException;

public class Game {
    private GameContext context;

    public Game() {
        this.context = GameContext.getInstance();
    }

    public void start() {
        GameLogger.log("Gioco iniziato");
        System.out.println("Benvenuto in Dungeon Explorer!");
        // Loop principale
        while (!context.isGameOver()) {
            try {
                System.out.print("> ");
                String input = InputSanitizer.readInput();
                CommandProcessor.process(input);
            } catch (GameException e) {
                System.out.println(e.getUserMessage());
                GameLogger.logError(e);
            }
        }
    }
}
