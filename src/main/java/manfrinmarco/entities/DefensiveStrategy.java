package manfrinmarco.entities;

public class DefensiveStrategy implements EnemyStrategy {
    @Override
    public void attack(Enemy enemy, Player player) {
        if (enemy.getHealth() < 20) {
            enemy.takeDamage(-5);
        } else {
            player.takeDamage(5);
        }
    }
}