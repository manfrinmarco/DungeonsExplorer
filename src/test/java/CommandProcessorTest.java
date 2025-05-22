import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manfrinmarco.core.CommandProcessor;
import manfrinmarco.core.GameContext;
import manfrinmarco.entities.Player;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;
import manfrinmarco.map.Room;

public class CommandProcessorTest {

    private CommandProcessor processor;
    private Player player;
    private Room room;
    private Inventory inventory;

    @BeforeEach
    public void setup() {
        processor = new CommandProcessor();

        player = new Player("TestPlayer", 100);
        inventory = player.getInventory();
        room = new Room("testRoom", "Una stanza usata per i test");

        GameContext.getInstance().setPlayer(player);
        GameContext.getInstance().setCurrentRoom(room);
    }

    @Test
    public void testShowStatusCommand() {
        processor.processCommand("status");
        assert player.getHealth() == 100;
    }

    @Test
    public void testPickItemCommand() {
        Item item = new Item("Spada", ItemType.WEAPON, 0);
        room.addItem(item);

        processor.processCommand("take Spada");

        assert inventory.contains(item);
    }
}
