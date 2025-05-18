package manfrinmarco.items;

public class ItemBuilder {
    private String name;
    private ItemType type;

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setType(ItemType type) {
        this.type = type;
        return this;
    }

    public Item build() {
        return new Item(name, type);
    }
}