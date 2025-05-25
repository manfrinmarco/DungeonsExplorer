package manfrinmarco.entities;

import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.config.GameConfig;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;

public class Player extends Entity{
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(Player.class.getName());

    private Inventory inventory;
    private Item equippedWeapon;
    private Item equippedArmor;
    private int baseDamage;

    public Player(String name) {
        super(name, GameConfig.getInt("player.hp"));
        this.inventory = new Inventory();
        try {
            baseDamage = Integer.parseInt(GameConfig.get("player.basedamage"));
        } catch (NumberFormatException e) {
            System.err.println("Valore di player.basedamage non valido, uso fallback a 10.");
            baseDamage = 10;
        }
        log.log(Level.INFO, "Player creato: {0} con HP iniziali: {1}", new Object[]{name, this.health});
    }

    public Player(String name, int health) {
        super(name, health);
        this.inventory = new Inventory();
        try {
            baseDamage = Integer.parseInt(GameConfig.get("player.basedamage"));
        } catch (NumberFormatException e) {
            System.err.println("Valore di player.basedamage non valido, uso fallback a 10.");
            baseDamage = 10;
        }
        log.log(Level.INFO, "Player creato: {0} con HP iniziali: {1}", new Object[]{name, this.health});
    }

    @Override
    public void attack(Entity enemy) {
        log.log(Level.FINE, "Player.attack: tentativo di attacco con equipaggiamento: {0}", equippedWeapon != null ? equippedWeapon.getName() : "nessuno");
        int damage = baseDamage;
        if (equippedWeapon != null) {
            damage += equippedWeapon.getPower();
        }
        enemy.takeDamage(damage);
        log.log(Level.FINE, "Player.attack: danno totale calcolato = {0}", baseDamage);
        enemy.takeDamage(baseDamage);
        log.log(Level.INFO, "Player.attack: inflitti {0} danni a {1}", new Object[]{baseDamage, enemy.getName()});
    }

    @Override
    public void takeDamage(int damage) {
        log.log(Level.FINE, "Player.takeDamage: danno in ingresso = {0}", damage);
        int mitigated = damage;
        if (equippedArmor != null) {
            mitigated = Math.max(0, damage - equippedArmor.getPower());
        }
        log.log(Level.FINE, "Player.takeDamage: danno dopo armatura = {0}", mitigated);
        super.takeDamage(mitigated);
        log.log(Level.INFO, "Player.takeDamage: HP rimanenti = {0}", this.health);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void equip(Item item) {
        switch (item.getType()) {
            case WEAPON -> {
                if (equippedWeapon != null) {
                    inventory.addItem(equippedWeapon);
                    System.out.println("Hai disarmato l'arma: " + equippedWeapon.getName());
                    log.log(Level.INFO, "Player.equip: disarmato weapon = {0}", equippedWeapon.getName());
                }
                this.equippedWeapon = item;
                this.inventory.removeItem(item);
                System.out.println("Hai equipaggiato l'arma: " + item.getName());
                log.log(Level.INFO, "Player.equip: equipaggiato weapon = {0}", item.getName());
            }
            case ARMOR -> {
                if(equippedArmor != null) {
                    inventory.addItem(equippedArmor);
                    System.out.println("Ti sei tolto l'armatura: " + equippedArmor.getName());
                    log.log(Level.INFO, "Player.equip: disarmato armor = {0}", equippedArmor.getName());
                }
                this.equippedArmor = item;
                this.inventory.removeItem(item);
                System.out.println("Hai indossato l'armatura: " + item.getName());
                log.log(Level.INFO, "Player.equip: equipaggiato armor = {0}", item.getName());
            }
            default -> {
                log.log(Level.WARNING, "Player.equip: tentativo di equipaggiare oggetto non valido = {0}", item.getName());
                System.out.println("Questo oggetto non può essere equipaggiato.");
            }
        }
    }

    public Item getEquippedArmor(){
        return this.equippedArmor;
    }

    public Item getEquippedWeapon(){
        return this.equippedWeapon;
    }

    public String getStatus() {
        String arma = (equippedWeapon != null) ? equippedWeapon.getName() + " (+" + equippedWeapon.getPower() + ")" : "Nessuna";
        String armatura = (equippedArmor != null) ? equippedArmor.getName() + " (+" + equippedArmor.getPower() + ")" : "Nessuna";
        return String.format("HP: %d | Arma: %s | Armatura: %s", this.health, arma, armatura);
    }

    public void heal(int amount) {
        log.log(Level.FINE, "Player.heal: tentativo di guarigione di {0} HP", amount);
        this.health = Math.min(100, this.health + amount);
        log.log(Level.INFO, "Player.heal: HP attuali = {0}", this.health);
    }

    public int getBaseDamage() {
        return this.baseDamage;
    }

    public void setPower(int i) {
        this.baseDamage = i;
    }
}