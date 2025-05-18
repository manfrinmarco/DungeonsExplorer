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

    private Room mainRoom;

    public void setMainRoom(Room room) {
        this.mainRoom = room;
    }

    public Room getMainRoom() {
        return mainRoom;
    }

    public List<Room> getSubRooms() {
        return subRooms;
    }
}
