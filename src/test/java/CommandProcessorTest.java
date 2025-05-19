import manfrinmarco.core.CommandProcessor;
import manfrinmarco.core.GameContext;
import manfrinmarco.entities.Player;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;
import manfrinmarco.map.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CommandProcessorTest {

    private CommandProcessor processor;
    private GameContext contextMock;
    private Player playerMock;
    private Room roomMock;
    private Inventory inventoryMock;

    @BeforeEach
    public void setup() {
        processor = new CommandProcessor();

        contextMock = mock(GameContext.class);
        playerMock = mock(Player.class);
        roomMock = mock(Room.class);
        inventoryMock = mock(Inventory.class);

        when(contextMock.getPlayer()).thenReturn(playerMock);
        when(contextMock.getCurrentRoom()).thenReturn(roomMock);
        when(playerMock.getInventory()).thenReturn(inventoryMock);

        // Override GameContext singleton with mock (optional if you can refactor GameContext)
        GameContext originalContext = GameContext.getInstance();
        originalContext.setPlayer(playerMock);
        originalContext.setCurrentRoom(roomMock);
    }

    @Test
    public void testShowStatusCommand() {
        when(playerMock.getHealth()).thenReturn(80);
        processor.processCommand("stato");

        verify(playerMock, times(1)).getHealth();
    }

    @Test
    public void testPickItemCommand() {
        Item mockItem = mock(Item.class);
        when(mockItem.getName()).thenReturn("Spada");
        when(roomMock.getItems()).thenReturn(java.util.List.of(mockItem));

        processor.processCommand("prendi Spada");

        verify(inventoryMock).addItem(mockItem);
        verify(roomMock).removeItem(mockItem);
    }

    @Test
    public void testEquipItemCommand() {
        Item mockItem = mock(Item.class);
        when(mockItem.getName()).thenReturn("Elmo");
        when(inventoryMock.iterator()).thenReturn(java.util.List.of(mockItem).iterator());

        processor.processCommand("equip Elmo");

        verify(playerMock).equip(mockItem);
    }

    @Test
    public void testUsePotionCommand() {
        Item potion = new Item("Pozione", ItemType.POTION, 0);
        when(inventoryMock.iterator()).thenReturn(java.util.List.of(potion).iterator());

        processor.processCommand("usa Pozione");

        verify(playerMock).heal(anyInt());
        verify(inventoryMock).removeItem(potion);
    }
}
