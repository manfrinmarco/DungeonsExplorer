package manfrinmarco.entities;
import manfrinmarco.items.Item;

public class Enemy extends Entity {
    private final EnemyStrategy strategy;

    public Enemy(String name, int health, EnemyStrategy strategy) {
        super(name, health);
        this.strategy = strategy;
    }

    public EnemyStrategy getStrategy() {
        return strategy;
    }

    public void executeStrategy(Player player) {
        strategy.attack(this, player);
    }

    private Item drop;

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    public Item getDrop() {
      return drop;
    }
}
    