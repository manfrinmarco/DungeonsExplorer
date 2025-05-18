package manfrinmarco.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameFileManager {
    private static final String SAVE_FILE = "savegame.dat";

    public static void saveGame(String data) {
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            writer.write(data);
            System.out.println("Partita salvata.");
        } catch (IOException e) {
            System.err.println("Errore salvataggio: " + e.getMessage());
        }
    }

    public static String loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            System.out.println("Partita caricata.");
            return sb.toString();
        } catch (IOException e) {
            System.err.println("Errore caricamento: " + e.getMessage());
            return null;
        }
    }
}