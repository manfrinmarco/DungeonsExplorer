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

    @Override
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

    @Override
    public void takeDamage(int damage) {
        int mitigated = damage;
        if (equippedArmor != null) {
            mitigated = Math.max(0, damage - equippedArmor.getPower());
        }
        super.takeDamage(mitigated);
    }

    public String getStatus() {
        String arma = (equippedWeapon != null) ? equippedWeapon.getName() + " (+" + equippedWeapon.getPower() + ")" : "Nessuna";
        String armatura = (equippedArmor != null) ? equippedArmor.getName() + " (+" + equippedArmor.getPower() + ")" : "Nessuna";
        return String.format("HP: %d | Arma: %s | Armatura: %s", this.health, arma, armatura);
    }

    public void equip(Item item) {
        switch (item.getType()) {
            case WEAPON:
                this.equippedWeapon = item;
                System.out.println("Hai equipaggiato l'arma: " + item.getName());
                break;
            case ARMOR:
                this.equippedArmor = item;
                System.out.println("Hai indossato l'armatura: " + item.getName());
                break;
            default:
                System.out.println("Questo oggetto non può essere equipaggiato.");
                break;
        }
    }
}
    