package manfrinmarco.items.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemTemplate;
import manfrinmarco.items.ItemType;

@AutoLoad
public class ChiaveDorata implements ItemTemplate {
    @Override
    public Item create() {
        return new ItemBuilder()
                .setName("Chiave Dorata")
                .setType(ItemType.KEY)
                .setPower(0)
                .build();
    }

    @Override
    public String getName() {
        return "chiavedorata";
    }
}