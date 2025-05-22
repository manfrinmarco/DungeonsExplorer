package manfrinmarco.entities;

import manfrinmarco.items.Item;

public class Enemy extends Entity {
    private static final long serialVersionUID = 1L;

    private final EnemyStrategy strategy;
    private Item drop;

    public Enemy(String name, int health, EnemyStrategy strategy) {
        super(name, health);
        this.strategy = strategy;
    }

    public Item getDrop() {
      return drop;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    public EnemyStrategy getStrategy() {
        return strategy;
    }

    public void executeStrategy(Player player) {
        strategy.attack(this, player);
    }
}
    