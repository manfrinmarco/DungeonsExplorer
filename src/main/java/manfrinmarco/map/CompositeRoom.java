package manfrinmarco.map;

import java.util.ArrayList;
import java.util.List;

public class CompositeRoom extends Room {
    private final List<Room> subRooms = new ArrayList<>();

    private Room mainRoom;

    public CompositeRoom(String name, String description) {
        super(name, description);
    }

    public void addRoom(Room room) {
        subRooms.add(room);
    }

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
