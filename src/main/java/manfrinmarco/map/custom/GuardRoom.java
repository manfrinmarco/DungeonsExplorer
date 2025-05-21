package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class GuardRoom extends Room {
    public GuardRoom() {
        super("Guardie", "Stanza delle Guardie", "Ci sono delle guardie qui.");
    }
}