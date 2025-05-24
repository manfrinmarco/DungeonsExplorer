package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Corridor implements RoomTemplate {
    @Override
    public String getType() {
        return "corridoio";
    }

    @Override
    public Room create() {
        return new Room("Corridoio", "Un lungo corridoio buio.");
    }
}