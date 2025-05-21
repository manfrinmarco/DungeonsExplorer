package manfrinmarco.items.special;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemTemplate;
import manfrinmarco.items.ItemType;

@AutoLoad
public class CurativePotion implements ItemTemplate {
    @Override
    public Item create() {
        return new ItemBuilder()
                .setName("Pozione Curativa")
                .setType(ItemType.CURATIVE_POTION)
                .setPower(0)
                .build();
    }

    @Override
    public String getId() {
        return "Pozione Curativa";
    }
}