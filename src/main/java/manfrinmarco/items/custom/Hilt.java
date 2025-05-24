package manfrinmarco.items.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemTemplate;
import manfrinmarco.items.ItemType;

@AutoLoad
public class Hilt implements ItemTemplate {
    @Override
    public Item create() {
        return new ItemBuilder()
                .setName("Elsa")
                .setType(ItemType.WEAPON)
                .setPower(30)
                .setCombinable(true)
                .build();
    }

    @Override
    public String getName() {
        return "elsa";
    }
}