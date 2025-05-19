

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manfrinmarco.entities.AggressiveStrategy;
import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.Player;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;

public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Eroe", 100);
    }

    @Test
    public void testPlayerTakesDamageWithoutArmor() {
        player.takeDamage(30);
        assertEquals(70, player.getHealth());
    }

    @Test
    public void testPlayerTakesDamageWithArmor() {
        Item armor = new Item("Armatura Test", ItemType.ARMOR, 10);
        player.equip(armor);
        player.takeDamage(30);
        assertEquals(80, player.getHealth()); // 30 - 10 = 20 damage
    }

    @Test
    public void testPlayerHeals() {
        player.takeDamage(50);
        player.heal(30);
        assertEquals(80, player.getHealth());
    }

    @Test
    public void testPlayerAttackWithWeapon() {
        Enemy enemy = new Enemy("Dummy", 100, new AggressiveStrategy());
        Item weapon = new Item("Spada", ItemType.WEAPON, 15);
        player.equip(weapon);
        player.attack(enemy);
        assertTrue(enemy.getHealth() <= 90); // base 10 + 15 = 25
    }
}