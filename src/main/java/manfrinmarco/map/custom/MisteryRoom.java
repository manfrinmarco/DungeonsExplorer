package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class MisteryRoom implements RoomTemplate {
    @Override
    public String getType() {
        return "stanzaMisteriosa";
    }

    @Override
    public Room create() {
        return new Room("Stanza Misteriosa", "Stanza misteriosa.");
    }
}