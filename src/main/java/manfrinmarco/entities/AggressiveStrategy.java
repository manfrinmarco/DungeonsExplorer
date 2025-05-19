package manfrinmarco.entities;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AggressiveStrategy implements EnemyStrategy, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(AggressiveStrategy.class.getName());

    @Override
    public void attack(Enemy enemy, Player player) {
        log.log(Level.FINE, "AggressiveStrategy: {0} is attacking", enemy.getName());
        int damage = ThreadLocalRandom.current().nextInt(10, 21); // 10–20
        log.log(Level.FINE, "AggressiveStrategy: computed damage = {0}", damage);
        System.out.println("Il " + enemy.getName() + " attacca con furia causando " + damage + " danni!");
        player.takeDamage(damage);
        log.log(Level.FINE, "AggressiveStrategy: applied damage to player, player''s HP now {0}", player.getHealth());
    }
}