package manfrinmarco.map;

import java.util.ArrayList;
import java.util.List;

public class CompositeRoom extends Room {
    private final List<Room> subRooms = new ArrayList<>();

    public CompositeRoom(String name, String description) {
        super(name, description);
    }

    public void addRoom(Room room) {
        subRooms.add(room);
    }

    public List<Room> getSubRooms() {
        return subRooms;
    }
}