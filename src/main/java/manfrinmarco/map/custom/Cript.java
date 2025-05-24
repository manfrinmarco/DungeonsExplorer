package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Cript implements RoomTemplate {
    @Override
    public String getType() {
        return "cripta";
    }

    @Override
    public Room create() {
        return new Room("cripta", "Una cripta buia e fredda.");
    }
}