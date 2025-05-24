package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Garden implements RoomTemplate {
    @Override
    public String getType() {
        return "giardino";
    }

    @Override
    public Room create() {
        return new Room("Giardino", "Un giardino abbandonato ricoperto di erbacce.");
    }
}