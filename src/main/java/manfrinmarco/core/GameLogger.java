package manfrinmarco.core;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GameLogger {
    public static void configure() {
        Logger root = Logger.getLogger("");
        // rimuovi handler di default
        for (Handler h : root.getHandlers()) {
            root.removeHandler(h);
        }

        // Console -> to see logs in console uncomment the next lines (20-23)
        // ConsoleHandler console = new ConsoleHandler();
        // console.setLevel(Level.INFO);
        // console.setFormatter(new SimpleFormatter());
        // root.addHandler(console);

        // File rotating
        try {
            // 1 file da 1MB, 3 copie
            FileHandler file = new FileHandler("game.log", 1_000_000, 3, true);
            file.setLevel(Level.FINE);
            file.setFormatter(new SimpleFormatter());
            root.addHandler(file);
        } catch (IOException e) {
            root.log(Level.SEVERE, "Failed to setup file handler", e);
        }

        root.setLevel(Level.FINE);  // imposta livello minimo globale
    }
}