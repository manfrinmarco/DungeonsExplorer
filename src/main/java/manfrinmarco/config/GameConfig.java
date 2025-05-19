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
        String value = config.getProperty(key);
        if (value == null) {
            throw new manfrinmarco.security.GameException("Chiave di configurazione mancante: " + key);
        }
        return value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static double getDouble(String key) {
        return Double.parseDouble(get(key));
    }

    public static String getOrDefault(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }
} 