package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Cell implements RoomTemplate {
    @Override
    public String getType() {
        return "cella";
    }

    @Override
    public Room create() {
        return new Room("cella", "Una piccola cella abbandonata.");
    }
}