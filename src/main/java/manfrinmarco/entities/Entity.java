package manfrinmarco.entities;

import java.io.Serializable;

public abstract class Entity implements Serializable{
    private static final long serialVersionUID = 1L;
    
    protected String name;
    protected int health;

    public Entity(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void attack(Entity enemy) {
        int baseDamage = 0;
        enemy.takeDamage(baseDamage);
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }
}