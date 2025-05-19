package manfrinmarco.core;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class GameLogger {
    private static final String LOG_FILE = manfrinmarco.config.GameConfig.get("log.file");

    public static void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(LocalDateTime.now() + ": " + message + "\n");
        } catch (IOException e) {
            System.err.println("Errore nel logging: " + e.getMessage());
        }
    }
}