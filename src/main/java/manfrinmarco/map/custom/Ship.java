package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Ship implements RoomTemplate {
    @Override
    public String getType() {
        return "nave";
    }

    @Override
    public Room create() {
        return new Room("Nave", "Una vecchia nave attraccata al porto.");
    }
}