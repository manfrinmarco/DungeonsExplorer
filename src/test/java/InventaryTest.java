import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;

public class InventaryTest {
    private Inventory inventory;
    private Item sword;
    private Item potion;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        sword = new Item("Spada", ItemType.WEAPON, 10);
        potion = new Item("Pozione", ItemType.POTION, 0);
    }

    @Test
    public void testAddItem() {
        inventory.addItem(sword);
        assertTrue(inventory.iterator().hasNext());
    }

    @Test
    public void testRemoveItem() {
        inventory.addItem(potion);
        inventory.removeItem(potion);
        assertFalse(inventory.iterator().hasNext());
    }

    @Test
    public void testInventoryContainsItems() {
        inventory.addItem(sword);
        inventory.addItem(potion);

        int count = 0;
        for (Item item : inventory) {
            count++;
            assertTrue(item.getName().equals("Spada") || item.getName().equals("Pozione"));
        }
        assertEquals(2, count);
    }
}
