package manfrinmarco.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameConfig {
    private static final Properties config = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("game.properties")) {
            config.load(fis);
        } catch (IOException e) {
            System.err.println("Errore caricamento configurazione: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return config.getProperty(key);
    }
}