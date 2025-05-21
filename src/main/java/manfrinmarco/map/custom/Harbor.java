package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class Harbor extends Room {
    public Harbor() {
        super("Porto", "Porto", "Un porto di legno, bagnato dalle onde.");
    }
}