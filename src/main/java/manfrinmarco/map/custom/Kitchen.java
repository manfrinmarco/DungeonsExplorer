package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Kitchen implements RoomTemplate {
    @Override
    public String getType() {
        return "cucina";
    }

    @Override
    public Room create() {
        return new Room("Cucina", "Sei in cucina, c'è un forte odore di cibo andato a male");
    }
}