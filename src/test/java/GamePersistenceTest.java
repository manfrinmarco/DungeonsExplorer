

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manfrinmarco.core.GameContext;
import manfrinmarco.core.GameStateMemento;
import manfrinmarco.entities.Player;
import manfrinmarco.io.GameFileManager;
import manfrinmarco.items.Inventory;
import manfrinmarco.map.Room;
import manfrinmarco.security.GameException;

public class GamePersistenceTest {

    private static final String SAVE_FILE = "savegame.dat";

    @BeforeEach
    public void setUp() {
        // ensure no leftover file
        File f = new File(SAVE_FILE);
        if (f.exists()) f.delete();
    }

    @AfterEach
    public void tearDown() {
        File f = new File(SAVE_FILE);
        if (f.exists()) f.delete();
    }

    @Test
    public void testSaveAndLoadMemento_preservesState() {
        GameContext context = GameContext.getInstance();
        // configure a simple state
        Player player = new Player("Tester", 50);
        Inventory inv = new Inventory();
        player.setInventory(inv);
        context.setPlayer(player);
        Room room = new Room("TestRoom", "A room for testing");
        context.setCurrentRoom(room);
        context.increaseScore(10);

        // take a snapshot and save
        GameStateMemento memento = new GameStateMemento(context);
        GameFileManager.saveMemento(memento);

        // modify context to ensure load actually resets
        context.getPlayer().heal(-30); // reduce HP to 20
        context.setCurrentRoom(new Room("Another", "Different"));
        context.increaseScore(5);

        // load back
        GameStateMemento loaded = GameFileManager.loadMemento();
        assertNotNull(loaded, "Loaded memento should not be null");
        GameContext restored = loaded.getSnapshot();

        // verify fields match original snapshot
        assertEquals(50, restored.getPlayer().getHealth(), "Player HP should be restored");
        assertEquals("TestRoom", restored.getCurrentRoom().getName(), "Current room should be restored");
        assertEquals(10, restored.getScore(), "Score should be restored");
    }

    @Test
    public void testLoadMemento_whenNoFile_returnsNull() {
        Exception exception = assertThrows(GameException.class, () -> {
            GameFileManager.loadMemento();
        });
        assertTrue(exception.getMessage().contains("Errore caricamento"));
    }
}
