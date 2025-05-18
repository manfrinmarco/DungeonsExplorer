package manfrinmarco.map;

import java.util.Iterator;
import java.util.List;

public class RoomIterator implements Iterator<Room> {
    private final Iterator<Room> iterator;

    public RoomIterator(List<Room> rooms) {
        this.iterator = rooms.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Room next() {
        return iterator.next();
    }
}