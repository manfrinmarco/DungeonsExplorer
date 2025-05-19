package manfrinmarco.entities;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class AggressiveStrategy implements EnemyStrategy, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public void attack(Enemy enemy, Player player) {
        int damage = ThreadLocalRandom.current().nextInt(10, 21); // 10–20
        System.out.println("Il " + enemy.getName() + " attacca con furia causando " + damage + " danni!");
        player.takeDamage(damage);
    }
}