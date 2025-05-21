package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class Ship extends Room {
    public Ship() {
        super("Nave", "Nave", "Una vecchia nave attraccata al porto.");
    }
}