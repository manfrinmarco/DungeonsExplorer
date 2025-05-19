package manfrinmarco.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory implements Iterable<Item>, Serializable{
    private static final long serialVersionUID = 1L;
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}