import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import manfrinmarco.items.Item;
import manfrinmarco.items.ItemFactory;
import manfrinmarco.items.ItemType;

public class ItemFactoryTest {

    @Test
    void testCreateValidItem() {
        Item item = ItemFactory.create("pozione");
        assertNotNull(item);
        assertEquals("Pozione Curativa", item.getName());
        assertEquals(ItemType.CURATIVE_POTION, item.getType());
    }

    @Test
    void testCreateInvalidItemThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ItemFactory.create("non_esiste");
        });
        assertTrue(exception.getMessage().contains("Item non trovato"));
    }
}
