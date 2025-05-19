package manfrinmarco.items.special;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemTemplate;
import manfrinmarco.items.ItemType;

@AutoLoad
public class AnelloDelFuoco implements ItemTemplate {
    @Override
    public Item create() {
        return new ItemBuilder()
                .setName("Anello del Fuoco")
                .setType(ItemType.WEAPON)
                .setPower(15)
                .build();
    }

    @Override
    public String getId() {
        return "anellofuoco";
    }
}