package manfrinmarco.items.special;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;
    
@AutoLoad
public class TorciaMagica extends Item {
    public TorciaMagica() {
        super("Torcia Magica", ItemType.TOOL, 0);
    }
}
