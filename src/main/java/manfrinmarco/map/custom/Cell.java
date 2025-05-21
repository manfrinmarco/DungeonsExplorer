package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class Cell extends Room {
    public Cell() {
        super("cella", "cella", "Una piccola cella abbandonata.");
    }
}