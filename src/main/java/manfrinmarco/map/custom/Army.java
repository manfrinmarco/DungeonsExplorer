package manfrinmarco.map.custom;

import manfrinmarco.annotations.AutoLoad;
import manfrinmarco.map.Room;

@AutoLoad
public class Army extends Room {
    public Army() {
        super("Armeria", "Armeria", "Contiene armi abbandonate.");
    }
}