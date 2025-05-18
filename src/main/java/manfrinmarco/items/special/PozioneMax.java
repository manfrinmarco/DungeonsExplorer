package manfrinmarco.items.special;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;

@AutoLoad
public class PozioneMax extends Item {
    public PozioneMax() {
        super("Pozione Max", ItemType.POTION, 0);
    }
}