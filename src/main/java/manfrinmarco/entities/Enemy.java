package manfrinmarco.entities;

public class Enemy extends Entity {
    private EnemyStrategy strategy;

    public Enemy(String name, int health, EnemyStrategy strategy) {
        super(name, health);
        this.strategy = strategy;
    }

    public void executeStrategy(Player player) {
        strategy.attack(this, player);
    }
}