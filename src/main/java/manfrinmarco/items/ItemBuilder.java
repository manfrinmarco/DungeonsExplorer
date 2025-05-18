package manfrinmarco.items;

public class ItemBuilder {
    private String name;
    private ItemType type;
    private int power;

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setType(ItemType type) {
        this.type = type;
        return this;
    }

    public ItemBuilder setPower(int power) {
        this.power = power;
        return this;
    }

    public Item build() {
        return new Item(name, type, power);
    }
}