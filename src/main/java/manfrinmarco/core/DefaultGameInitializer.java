package manfrinmarco.core;

import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.EnemyFactory;
import manfrinmarco.entities.Player;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemType;
import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomFactory;

public class DefaultGameInitializer {
    public static void initialize(GameContext context) {
        Player player = new Player("Eroe", 100);
        Inventory inventory = new Inventory();
        inventory.addItem(new ItemBuilder().setName("Pozione Curativa").setType(ItemType.POTION).build());
        player.setInventory(inventory);

        Room entrance = RoomFactory.createRoom("corridoio");
        Room cell = RoomFactory.createRoom("cella");
        Enemy goblin = EnemyFactory.createEnemy("goblin");
        cell.setEnemy(goblin);

        entrance.setExit(Direction.EAST, cell);
        cell.setExit(Direction.WEST, entrance);

        context.setPlayer(player);
        context.setCurrentRoom(entrance);
    }
}