package manfrinmarco.core;

import manfrinmarco.entities.Player;
import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomFactory;

public class DefaultGameInitializer {
    public static void initialize(GameContext context) {
        Player player = new Player("Eroe", 100);
        Room entrance = RoomFactory.createRoom("corridoio");
        Room cell = RoomFactory.createRoom("cella");

        entrance.setExit(Direction.EAST, cell);
        cell.setExit(Direction.WEST, entrance);

        context.setPlayer(player);
        context.setCurrentRoom(entrance);
    }
}