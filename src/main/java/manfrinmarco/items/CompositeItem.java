package manfrinmarco.items;

import java.util.ArrayList;
import java.util.List;

public class CompositeItem extends Item {
    
    private final List<Item> components = new ArrayList<>();

    public CompositeItem(String name) {
        super(name, ItemType.WEAPON);
    }

    public void addItem(Item item) {
        components.add(item);
    }

    public List<Item> getComponents() {
        return components;
    }

    public void setPower(int power) {
    this.power = power;
}
}