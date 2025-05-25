import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manfrinmarco.core.CommandProcessor;
import manfrinmarco.core.GameContext;
import manfrinmarco.entities.Player;
import manfrinmarco.map.Room;

public class InvalidCommandTest {
    private CommandProcessor processor;

    @BeforeEach
    public void setup() {
        processor = new CommandProcessor();
        Player player = new Player("Test", 100);
        GameContext.getInstance().setPlayer(player);
        GameContext.getInstance().setCurrentRoom(new Room("test", "desc"));
    }

    @Test
    public void testUnknownCommand() {
        assertDoesNotThrow(() -> processor.processCommand("vola via"));
    }

    @Test
    public void testEmptyCommand() {
        assertDoesNotThrow(() -> processor.processCommand(""));
    }
}