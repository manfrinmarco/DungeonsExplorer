package manfrinmarco.core;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class GameLogger {
    private static final String LOG_FILE = "game.log";

    public static void log(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(LocalDateTime.now() + " INFO: " + message + "\n");
        } catch (IOException e) {
            System.err.println("Errore nel logging.");
        }
    }

    public static void logError(Exception e) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(LocalDateTime.now() + " ERROR: " + e.getMessage() + "\n");
        } catch (IOException ex) {
            System.err.println("Errore nel logging degli errori.");
        }
    }
}
