package manfrinmarco.items.special;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemTemplate;
import manfrinmarco.items.ItemType;

@AutoLoad
public class MagicPotion implements ItemTemplate {
    @Override
    public Item create() {
        return new ItemBuilder()
                .setName("p")
                .setType(ItemType.MAGIC_POTION)
                .setPower(15)
                .build();
    }

    @Override
    public String getId() {
        return "p";
    }
}