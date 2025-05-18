package manfrinmarco.items.special;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;

@AutoLoad
public class ChiaveDorata extends Item {
    public ChiaveDorata() {
        super("Chiave Dorata", ItemType.KEY, 0);
    }
}