package manfrinmarco.items;

public class Item {
    protected String name;
    protected ItemType type;
    protected int power;

    public Item(String name, ItemType type) {
        this.name = name;
        this.type = type;
    }

    public Item(String name, ItemType type, int power) {
        this.name = name;
        this.type = type;
        this.power = power;
    }

    @Override
    public String toString() {
        return name + " (" + type + (power > 0 ? ", +" + power : "") + ")";
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getPower() {
        return power;
    }
}