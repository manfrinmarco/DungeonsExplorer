package manfrinmarco.core;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import manfrinmarco.config.GameConfig;

public class GameLogger {
    public static void configure() {
        Logger root = Logger.getLogger("");

        // Rimuovi gli handler di default
        for (Handler h : root.getHandlers()) {
            root.removeHandler(h);
        }

        // Crea la cartella logs se non esiste
        File logDir = new File(GameConfig.get("log.dir"));
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        // File rotating nella cartella logs
        try {
            File logFile = new File(logDir, GameConfig.get("log.file"));  // logs/game.log
            FileHandler file = new FileHandler(logFile.getPath(), 1_000_000, 3, true);
            file.setLevel(Level.FINE);
            file.setFormatter(new SimpleFormatter());
            root.addHandler(file);
        } catch (IOException e) {
            root.log(Level.SEVERE, "Failed to setup file handler", e);
        }

        root.setLevel(Level.FINE);  // Imposta il livello minimo globale
    }
}