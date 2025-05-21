package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class Exit extends Room {
    public Exit() {
        super("Uscita", "Uscita", "HAI VINTO!");
    }
}