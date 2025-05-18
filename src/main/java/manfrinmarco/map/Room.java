package manfrinmarco.map;

import java.util.EnumMap;
import java.util.Map;

import manfrinmarco.entities.Enemy;

public class Room {
    protected String name;
    protected String description;
    protected Map<Direction, Room> exits = new EnumMap<>(Direction.class);
    protected Enemy enemy;

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
}