package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class Army implements RoomTemplate {
    @Override
    public String getType() {
        return "armeria";
    }

    @Override
    public Room create() {
        return new Room("Armeria", "Questa deve essere stata la stanza degli armamenti! Ci sono armi e armature abbandonate.");
    }
}