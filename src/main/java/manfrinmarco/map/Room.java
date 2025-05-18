package manfrinmarco.map;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import manfrinmarco.entities.Enemy;
import manfrinmarco.items.Item;

public class Room {
    protected String name;
    protected String description;
    protected Map<Direction, Room> exits = new EnumMap<>(Direction.class);
    protected Enemy enemy;
    protected List<Item> items = new ArrayList<>();
    protected boolean locked = false;
    protected Item keyRequired;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setExit(Direction direction, Room room) {
        exits.put(direction, room);
    }

    public Room getExit(Direction direction) {
        return exits.get(direction);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked, Item keyRequired) {
        this.locked = locked;
        this.keyRequired = keyRequired;
    }

    public boolean unlock(Item key) {
        if (locked && keyRequired != null && key.getName().equalsIgnoreCase(keyRequired.getName())) {
            locked = false;
            return true;
        }
        return false;
    }
}