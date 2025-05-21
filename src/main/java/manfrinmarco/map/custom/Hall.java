package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class Hall extends Room {
    public Hall() {
        super("entrata", "entrata", "L'ingresso del castello.");
    }
}