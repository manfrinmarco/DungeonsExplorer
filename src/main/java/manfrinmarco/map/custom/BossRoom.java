package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomTemplate;

@AutoLoad
public class BossRoom implements RoomTemplate {
    @Override
    public String getType() {
        return "bossRoom";
    }

    @Override
    public Room create() {
        return new Room("Stanza del Bosss", "C'è un trono vuoto, senti una presenza con una forza smisurata.");
    }
}