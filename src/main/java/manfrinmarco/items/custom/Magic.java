package manfrinmarco.items.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemTemplate;
import manfrinmarco.items.ItemType;

@AutoLoad
public class Magic implements ItemTemplate {
    @Override
    public Item create() {
        return new ItemBuilder()
                .setName("Magia")
                .setType(ItemType.MAGIC)
                .setPower(30)
                .build();
    }

    @Override
    public String getName() {
        return "magia";
    }
}