package manfrinmarco.entities;

public class Player extends Entity {
    public Player(String name, int health) {
        super(name, health);
    }

    public void attack(Entity enemy) {
        enemy.takeDamage(10);
    }
}