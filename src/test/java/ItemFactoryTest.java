import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import manfrinmarco.items.Item;
import manfrinmarco.items.ItemFactory;
import manfrinmarco.items.ItemType;
import manfrinmarco.security.GameException;

public class ItemFactoryTest {

    @Test
    void testCreateValidItem() {
        Item item = ItemFactory.createItem("pozione");
        assertNotNull(item);
        assertEquals("Pozione", item.getName());
        assertEquals(ItemType.POTION, item.getType());
    }

    @Test
    void testCreateInvalidItemThrowsException() {
        Exception exception = assertThrows(GameException.class, () -> {
            ItemFactory.createItem("non_esiste");
        });
        assertTrue(exception.getMessage().contains("Item non trovato"));
    }
}
