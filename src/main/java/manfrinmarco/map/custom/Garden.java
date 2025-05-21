package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class Garden extends Room {
    public Garden() {
        super("Giardino", "Giardino", "Un giardino abbandonato ricoperto di erbacce.");
    }
}