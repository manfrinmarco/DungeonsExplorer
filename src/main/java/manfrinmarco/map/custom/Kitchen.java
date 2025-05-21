package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class Kitchen extends Room {
    public Kitchen() {
        super("Cucina", "Cucina", "Odore di stufato e tre pozioni dimenticate.");
    }
}