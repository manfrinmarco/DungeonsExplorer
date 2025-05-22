package manfrinmarco.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Inventory implements Iterable<Item>, Serializable{
    private static final Logger log = Logger.getLogger(Inventory.class.getName());
    private static final long serialVersionUID = 1L;
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
        log.log(Level.INFO, "Inventory: aggiunto oggetto {0}", item.getName());
    }

    public void removeItem(Item item) {
        items.remove(item);
        log.log(Level.INFO, "Inventory: rimosso oggetto {0}", item.getName());
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    public Stream<Item> stream() {
        return items.stream();
    }
}