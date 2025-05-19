package manfrinmarco.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import manfrinmarco.core.GameStateMemento;

public class GameFileManager {
    private static final String SAVE_FILE = "savegame.dat";

    public static void saveMemento(GameStateMemento memento) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(memento);
            System.out.println("Partita salvata.");
        } catch (IOException e) {
            System.err.println("Errore salvataggio: " + e.toString ());
        }
    }

    public static GameStateMemento loadMemento() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            GameStateMemento memento = (GameStateMemento) ois.readObject();
            System.out.println("Partita caricata.");
            return memento;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore caricamento: " + e.toString());
            return null;
        }
    }
}