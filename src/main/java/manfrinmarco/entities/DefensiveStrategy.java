package manfrinmarco.entities;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefensiveStrategy implements EnemyStrategy, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(DefensiveStrategy.class.getName());

    @Override
    public void attack(Enemy enemy, Player player) {
        log.log(Level.FINE, "DefensiveStrategy: attacco iniziato da {0}", enemy.getName());
        if (enemy.getHealth() < 20) {
            log.fine("DefensiveStrategy: health < 20, preparazione a curare");
            int heal = ThreadLocalRandom.current().nextInt(3, 9); // 3–8
            log.log(Level.FINE, "DefensiveStrategy: calcolato heal = {0}", heal);
            System.out.println("Il " + enemy.getName() + " si cura di " + heal + " HP!");
            enemy.takeDamage(-heal);
            log.log(Level.FINE, "DefensiveStrategy: applicato heal, HP nemico ora {0}", enemy.getHealth());
        } else {
            log.fine("DefensiveStrategy: health >= 20, preparazione a infliggere danni");
            int damage = ThreadLocalRandom.current().nextInt(3, 8); // 3–7
            log.log(Level.FINE, "DefensiveStrategy: calcolato damage = {0}", damage);
            System.out.println("Il " + enemy.getName() + " colpisce cautamente causando " + damage + " danni.");
            player.takeDamage(damage);
            log.log(Level.FINE, "DefensiveStrategy: danni applicati, HP giocatore ora {0}", player.getHealth());
        }
    }
}