package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class BossRoom extends Room {
    public BossRoom() {
        super("Boss", "Stanza del Boss", "Un potente nemico ti aspetta qui.");
    }
}

