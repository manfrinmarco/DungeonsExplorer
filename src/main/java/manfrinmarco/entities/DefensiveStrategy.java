package manfrinmarco.entities;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class DefensiveStrategy implements EnemyStrategy, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void attack(Enemy enemy, Player player) {
        if (enemy.getHealth() < 20) {
            int heal = ThreadLocalRandom.current().nextInt(3, 9); // 3–8
            System.out.println("Il " + enemy.getName() + " si cura di " + heal + " HP!");
            enemy.takeDamage(-heal);
        } else {
            int damage = ThreadLocalRandom.current().nextInt(3, 8); // 3–7
            System.out.println("Il " + enemy.getName() + " colpisce cautamente causando " + damage + " danni.");
            player.takeDamage(damage);
        }
    }
}