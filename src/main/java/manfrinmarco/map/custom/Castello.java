package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Castello implements RoomTemplate {
    @Override
    public String getType() {
        return "castello";
    }

    @Override
    public Room create() {
        return new Room("Castello", "Un castello antico e misterioso.");
    }
}