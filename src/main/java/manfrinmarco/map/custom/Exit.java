package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Exit implements RoomTemplate {
    @Override
    public String getType() {
        return "uscita";
    }

    @Override
    public Room create() {
        return new Room("Uscita", "Sei riuscito ad uscire dal dungeon. HAI VINTO!");
    }
}