import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.EnemyFactory;
import manfrinmarco.security.GameException;

public class EnemyFactoryTest {

    @Test
    public void testCreateKnownEnemy() {
        Enemy goblin = EnemyFactory.createEnemy("goblin");
        assertNotNull(goblin);
        assertEquals("Goblin", goblin.getName());
        assertTrue(goblin.getHealth() > 0);
    }

    @Test
    public void testCreateUnknownEnemyThrowsException() {
        Exception exception = assertThrows(GameException.class, () -> {
            EnemyFactory.createEnemy("drago");
        });
        assertTrue(exception.getMessage().contains("Nemico non trovato"));
    }

    @Test
    public void testCreateCaseInsensitive() {
        Enemy enemy = EnemyFactory.createEnemy("SKELETON");
        assertEquals("Scheletro", enemy.getName());
    }
}
