package manfrinmarco.core;

import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.EnemyFactory;
import manfrinmarco.entities.Player;
import manfrinmarco.events.ScoreListener;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemBuilder;
import manfrinmarco.items.ItemType;
import manfrinmarco.map.CompositeRoom;
import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomFactory;

public class DefaultGameInitializer {
    public static void initialize(GameContext context) {
        Player player = new Player("Eroe", 100);
        Inventory inventory = new Inventory();

        Item pozione = new ItemBuilder().setName("Pozione")
                                        .setType(ItemType.POTION)
                                        .setPower(0)
                                        .build();
        inventory.addItem(pozione);
        player.setInventory(inventory);
        context.setPlayer(player);

        // Dungeon composto (CompositeRoom)
        Room entrata = RoomFactory.createRoom("corridoio");
        Room sala = RoomFactory.createRoom("corridoio");
        Room armeria = RoomFactory.createRoom("armeria");
        Room cripta = RoomFactory.createRoom("cripta");

        entrata.setExit(Direction.NORTH, sala);
        sala.setExit(Direction.SOUTH, entrata);
        sala.setExit(Direction.EAST, armeria);
        armeria.setExit(Direction.WEST, sala);
        sala.setExit(Direction.WEST, cripta);
        cripta.setExit(Direction.EAST, sala);

        CompositeRoom castello = new CompositeRoom("Castello Maledetto", "Un'antica fortezza infestata.");
        castello.addRoom(entrata);
        castello.addRoom(sala);
        castello.addRoom(armeria);
        castello.addRoom(cripta);
        castello.setMainRoom(entrata);
        
        // Nemici
        Enemy goblin = EnemyFactory.createEnemy("goblin");
        goblin.setDrop(new Item("Chiave della Cripta", ItemType.KEY));
        //entrata.setEnemy(goblin);

        Enemy scheletro = EnemyFactory.createEnemy("skeleton");
        scheletro.setDrop(new Item("Spada Rugginosa", ItemType.WEAPON, 10));
        sala.setEnemy(scheletro);

        Enemy guardiano = EnemyFactory.createEnemy("goblin");
        guardiano.setDrop(new Item("Elmo del Guardiano", ItemType.ARMOR, 8));
        cripta.setEnemy(guardiano);

        armeria.addItem(new Item("Scudo di Ferro", ItemType.ARMOR, 6));
        entrata.addItem(new Item("Armatura", ItemType.ARMOR, 10));
        entrata.addItem(new Item("Spada", ItemType.WEAPON, 5));


        context.setCurrentRoom(castello);
        context.setCurrentRoom(castello.getMainRoom());
        context.getEventManager().subscribe(new ScoreListener());
    }
}