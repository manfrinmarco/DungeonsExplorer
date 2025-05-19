package manfrinmarco.core;

import manfrinmarco.config.GameConfig;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.Player;
import manfrinmarco.events.ScoreListener;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;
import manfrinmarco.map.CompositeRoom;
import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;

public class DefaultGameInitializerDebug {
    public static void initialize(GameContext context) {
        Player player = new Player("Eroe", Integer.parseInt(GameConfig.get("player.hp")));
        Inventory inventory = new Inventory();
        player.setInventory(inventory);
        context.setPlayer(player);

        // stanze
        Room stanza1 = new Room("stanza1", "prima stanza");
        Room stanza2 = new Room("stanza2", "seconda stanza");
        Room stanza3 = new Room("stanza3", "terza stanza");

        //uscite
        stanza1.setExit(Direction.NORTH, stanza2);
        stanza2.setExit(Direction.SOUTH, stanza1);
        stanza2.setExit(Direction.NORTH, stanza3);
        stanza3.setExit(Direction.SOUTH, stanza2);

        //chiave
        Item chiave = new Item("Chiave", ItemType.KEY, 0);
        stanza1.addItem(chiave);
        stanza2.setLocked(true, chiave);

        //nemico
        Item spada = new Item("Spada", ItemType.WEAPON, 5);
        Enemy nemico = new Enemy("Nemico", 10, new DefensiveStrategy());
        stanza2.setEnemy(nemico);
        nemico.setDrop(spada);

        // Composizione castello
        CompositeRoom castello = new CompositeRoom("Castello", "Un castello antico e misterioso.");
        castello.addRoom(stanza1);
        castello.addRoom(stanza2);
        castello.addRoom(stanza3);
        castello.setMainRoom(stanza1);

        context.setCurrentRoom(castello.getMainRoom());
        context.getEventManager().subscribe(new ScoreListener());
    }
}
