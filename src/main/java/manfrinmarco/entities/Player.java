package manfrinmarco.entities;

import manfrinmarco.items.Inventory;

public class Player extends Entity {
    private Inventory inventory;

    public Player(String name, int health) {
        super(name, health);
        this.inventory = new Inventory();
    }

    public void attack(Entity enemy) {
        enemy.takeDamage(10);
    }

    public void heal(int amount) {
        this.health = Math.min(100, this.health + amount);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}