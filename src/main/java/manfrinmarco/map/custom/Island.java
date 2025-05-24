package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Island implements RoomTemplate {
    @Override
    public String getType() {
        return "isola";
    }

    @Override
    public Room create() {
        return new Room("Isola", "Un'isola deserta.");
    }
}