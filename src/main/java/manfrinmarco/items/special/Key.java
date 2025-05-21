package manfrinmarco.items.special;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemTemplate;
import manfrinmarco.items.ItemType;

@AutoLoad
public class Key implements ItemTemplate {
    @Override
    public Item create() {
        return new ItemBuilder()
                .setName("Chiave")
                .setType(ItemType.KEY)
                .setPower(0)
                .build();
    }

    @Override
    public String getId() {
        return "Chiave";
    }
}