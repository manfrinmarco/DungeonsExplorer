package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Harbor implements RoomTemplate {
    @Override
    public String getType() {
        return "porto";
    }

    @Override
    public Room create() {
        return new Room("Porto", "Un porto di legno, bagnato dalle onde.");
    }
}