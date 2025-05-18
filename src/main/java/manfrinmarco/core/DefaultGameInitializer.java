package manfrinmarco.core;

import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.EnemyFactory;
import manfrinmarco.entities.Player;
import manfrinmarco.events.ScoreListener;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;
import manfrinmarco.map.CompositeRoom;
import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;

public class DefaultGameInitializer {
    public static void initialize(GameContext context) {
        Player player = new Player("Eroe", 100);
        Inventory inventory = new Inventory();
        inventory.addItem(new Item("Pozione", ItemType.POTION));
        player.setInventory(inventory);
        context.setPlayer(player);

        // Dungeon composto (CompositeRoom)
        CompositeRoom castello = new CompositeRoom("Castello Maledetto", "Un'antica fortezza infestata.");
        Room entrata = new Room("Entrata", "L'ingresso del castello.");
        Room sala = new Room("Sala del Trono", "La grande sala del trono.");
        Room armeria = new Room("Armeria Oscura", "Contiene armi abbandonate.");
        Room cripta = new Room("Cripta", "Una cripta buia e fredda.");

        castello.addRoom(entrata);
        castello.addRoom(sala);
        castello.addRoom(armeria);
        castello.addRoom(cripta);

        entrata.setExit(Direction.NORTH, sala);
        sala.setExit(Direction.SOUTH, entrata);
        sala.setExit(Direction.EAST, armeria);
        armeria.setExit(Direction.WEST, sala);
        sala.setExit(Direction.WEST, cripta);
        cripta.setExit(Direction.EAST, sala);

        // Nemici
        Enemy goblin = EnemyFactory.createEnemy("goblin");
        goblin.setDrop(new Item("Chiave della Cripta", ItemType.KEY));
        entrata.setEnemy(goblin);

        Enemy scheletro = EnemyFactory.createEnemy("skeleton");
        scheletro.setDrop(new Item("Spada Rugginosa", ItemType.WEAPON, 10));
        sala.setEnemy(scheletro);

        Enemy guardiano = EnemyFactory.createEnemy("goblin");
        guardiano.setDrop(new Item("Elmo del Guardiano", ItemType.ARMOR, 8));
        cripta.setEnemy(guardiano);

        armeria.addItem(new Item("Scudo di Ferro", ItemType.ARMOR, 6));

        context.setCurrentRoom(entrata);
        context.getEventManager().subscribe(new ScoreListener());
    }
}