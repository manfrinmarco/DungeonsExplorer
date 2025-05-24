package manfrinmarco.items.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemTemplate;
import manfrinmarco.items.ItemType;

@AutoLoad
public class Armor implements ItemTemplate {
    @Override
    public Item create() {
        return new ItemBuilder()
                .setName("Armatura")
                .setType(ItemType.ARMOR)
                .setPower(30)
                .build();
    }

    @Override
    public String getName() {
        return "Armatura";
    }
}
