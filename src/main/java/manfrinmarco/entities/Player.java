package manfrinmarco.entities;

import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;

public class Player extends Entity {
    private Inventory inventory;
    private Item equippedWeapon;
    private Item equippedArmor;

    public Player(String name, int health) {
        super(name, health);
        this.inventory = new Inventory();
    }

    public void attack(Entity enemy) {
        int baseDamage = 10;
        if (equippedWeapon != null) {
            baseDamage += equippedWeapon.getPower();
        }
        enemy.takeDamage(baseDamage);
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

    public void equip(Item item) {
        if (item.getType() == manfrinmarco.items.ItemType.WEAPON) {
            this.equippedWeapon = item;
            System.out.println("Hai equipaggiato l'arma: " + item.getName());
        } else if (item.getType() == manfrinmarco.items.ItemType.ARMOR) {
            this.equippedArmor = item;
            System.out.println("Hai indossato l'armatura: " + item.getName());
        } else {
            System.out.println("Questo oggetto non può essere equipaggiato.");
        }
    }
}