package manfrinmarco.entities;

public class AggressiveStrategy implements EnemyStrategy {
    @Override
    public void attack(Enemy enemy, Player player) {
        player.takeDamage(15);
    }
}