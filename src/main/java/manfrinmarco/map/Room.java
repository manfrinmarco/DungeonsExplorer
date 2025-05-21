package manfrinmarco.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import manfrinmarco.entities.Enemy;
import manfrinmarco.items.Item;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    public String getName() {
        return name;
    }

    protected String description;
    public String getDescription() {
        return description;
    }

    protected Map<Direction, Room> exits = new EnumMap<>(Direction.class);
    public Room getExit(Direction direction) {
        return exits.get(direction);
    }
    public void setExit(Direction direction, Room room) {
        exits.put(direction, room);
    }

    protected boolean locked = false;
    protected Item keyRequired;
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

    protected Enemy enemy;
    public Enemy getEnemy() {
        return enemy;
    }
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    protected List<Item> items = new ArrayList<>();
    public void addItem(Item item) {
        items.add(item);
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
    public List<Item> getItems() {
        return items;
    }

    private CompositeRoom superRoom;
    public void setSuperRoom (CompositeRoom room){
        this.superRoom = room;
    }
    public CompositeRoom getSuperRoom (){
        return this.superRoom;
    }

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }
}