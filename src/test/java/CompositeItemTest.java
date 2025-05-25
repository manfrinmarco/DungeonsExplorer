import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import manfrinmarco.items.CompositeItem;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;

public class CompositeItemTest {

    @Test
    public void testAddItemsAndPower() {
        Item item1 = new Item("Spada", ItemType.WEAPON, 10);
        Item item2 = new Item("Scudo", ItemType.ARMOR, 5);

        CompositeItem composite = new CompositeItem("SpadaScudo");
        composite.addItem(item1);
        composite.addItem(item2);
        composite.setPower(item1.getPower() + item2.getPower());

        assertEquals(15, composite.getPower());
        assertTrue(composite.toString().contains("SpadaScudo"));
    }
}