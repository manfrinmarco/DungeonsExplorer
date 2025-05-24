package manfrinmarco.items.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemTemplate;
import manfrinmarco.items.ItemType;

@AutoLoad
public class Axe implements ItemTemplate {
    @Override
    public Item create() {
        return new ItemBuilder()
                .setName("Ascia")
                .setType(ItemType.WEAPON)
                .setPower(15)
                .build();
    }

    @Override
    public String getName() {
        return "Ascia";
    }
}