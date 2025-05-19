package manfrinmarco.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.core.GameStateMemento;

public class GameFileManager {
    private static final Logger log = Logger.getLogger(GameFileManager.class.getName());
    private static final String SAVE_FILE = "savegame.dat";

    public static void saveMemento(GameStateMemento memento) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(memento);
            log.info("Partita salvata.");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Errore salvataggio: {0}", e.toString());
        }
    }

    public static GameStateMemento loadMemento() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            GameStateMemento memento = (GameStateMemento) ois.readObject();
            log.info("Partita caricata.");
            return memento;
        } catch (IOException | ClassNotFoundException e) {
            log.log(Level.SEVERE, "Errore caricamento: {0}", e.toString());
            return null;
        }
    }
}