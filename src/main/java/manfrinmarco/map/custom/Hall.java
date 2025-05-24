package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Hall implements RoomTemplate {
    @Override
    public String getType() {
        return "entrata";
    }

    @Override
    public Room create() {
        return new Room("entrata", "L'ingresso del castello.");
    }
}