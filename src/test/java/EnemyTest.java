import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.AggressiveStrategy;
import manfrinmarco.entities.Player;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    @Test
    public void testEnemyTakesDamage() {
        Enemy enemy = new Enemy("Goblin", 30, new AggressiveStrategy());
        enemy.takeDamage(10);
        assertEquals(20, enemy.getHealth());
    }

    @Test
    public void testEnemyIsAlive() {
        Enemy enemy = new Enemy("Goblin", 1, new AggressiveStrategy());
        assertTrue(enemy.isAlive());

        enemy.takeDamage(1);
        assertFalse(enemy.isAlive());
    }

    @Test
    public void testSetAndGetDropItem() {
        Enemy enemy = new Enemy("Goblin", 30, new AggressiveStrategy());
        Item drop = new Item("Chiave", ItemType.KEY);
        enemy.setDrop(drop);

        assertNotNull(enemy.getDrop());
        assertEquals("Chiave", enemy.getDrop().getName());
    }

    @Test
    public void testStrategyAttackDoesNotCrash() {
        Enemy enemy = new Enemy("Goblin", 30, new AggressiveStrategy());
        Player player = new Player("Eroe", 100);
        enemy.executeStrategy(player);
        assertTrue(player.getHealth() < 100);
    }
}
