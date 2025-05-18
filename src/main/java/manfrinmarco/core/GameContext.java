package manfrinmarco.core;

import manfrinmarco.entities.Player;
import manfrinmarco.map.Room;

public class GameContext {
    private static final GameContext instance = new GameContext();

    private Player player;
    private Room currentRoom;

    private GameContext() {}

    public static GameContext getInstance() {
        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}