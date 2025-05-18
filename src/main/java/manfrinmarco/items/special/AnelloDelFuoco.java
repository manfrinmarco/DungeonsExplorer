package manfrinmarco.items.special;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;

@AutoLoad
public class AnelloDelFuoco extends Item {
    public AnelloDelFuoco() {
        super("Anello del Fuoco", ItemType.WEAPON, 15);
    }
}