package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class ThroneRoom implements RoomTemplate {
    @Override
    public String getType() {
        return "salaDelTrono";
    }

    @Override
    public Room create() {
        return new Room("sala", "La grande sala del trono.");
    }
}