import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import manfrinmarco.entities.Player;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerEquipmentTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Eroe", 100);
    }

    @Test
    public void testEquipAndReplaceWeapon() {
        Item sword = new Item("Spada", ItemType.WEAPON, 10);
        Item axe = new Item("Ascia", ItemType.WEAPON, 15);

        player.equip(sword);
        player.equip(axe);

        assertEquals(axe, player.getEquippedWeapon());
    }

    @Test
    public void testEquipArmor() {
        Item armor = new Item("Armatura", ItemType.ARMOR, 8);
        player.equip(armor);

        assertEquals(armor, player.getEquippedArmor());
    }
}