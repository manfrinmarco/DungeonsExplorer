package manfrinmarco.core;

import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final Logger log = Logger.getLogger(DefaultGameInitializerDebug.class.getName());
    @SuppressWarnings("LoggerStringConcat")
    public static void initialize(GameContext context) {
        log.info("DefaultGameInitializerDebug: inizio inizializzazione");
        Player player = new Player("Eroe", Integer.parseInt(GameConfig.get("player.hp")));
        Inventory inventory = new Inventory();
        player.setInventory(inventory);
        context.setPlayer(player);
        log.fine("Giocatore creato: " + player.getName() + ", HP=" + player.getHealth());

        // stanze
        Room stanza1 = new Room("stanza1", "prima stanza");
        Room stanza2 = new Room("stanza2", "seconda stanza");
        Room stanza3 = new Room("stanza3", "terza stanza");

        //uscite
        stanza1.setExit(Direction.NORTH, stanza2);
        stanza2.setExit(Direction.SOUTH, stanza1);
        stanza2.setExit(Direction.NORTH, stanza3);
        stanza3.setExit(Direction.SOUTH, stanza2);
        log.fine("Stanze create e collegate: stanza1, stanza2, stanza3");


        //chiave
        Item chiave = new Item("Chiave", ItemType.KEY, 0);
        stanza1.addItem(chiave);
        stanza2.setLocked(true, chiave);
        log.log(Level.FINE, "Chiave creata e stanza2 bloccata con chiave: {0}", chiave.getName());

        //chiave2
        Item chiave2 = new Item("Chiave2", ItemType.KEY, 0);
        stanza1.addItem(chiave2);

        //nemico
        Item spada = new Item("Spada", ItemType.WEAPON, 5);
        Enemy nemico = new Enemy("Nemico", 10, new DefensiveStrategy());
        stanza2.setEnemy(nemico);
        nemico.setDrop(spada);
        log.log(Level.FINE, "Nemico creato: {0} con HP={1} e drop={2}", new Object[]{nemico.getName(), nemico.getHealth(), spada.getName()});

        // Composizione castello
        CompositeRoom castello = new CompositeRoom("Castello", "Un castello antico e misterioso.");
        castello.addRoom(stanza1);
        castello.addRoom(stanza2);
        castello.addRoom(stanza3);
        castello.setMainRoom(stanza1);
        log.log(Level.FINE, "CompositeRoom ''Castello'' creato con stanze e mainRoom: {0}", castello.getMainRoom().getName());

        context.setCurrentRoom(castello.getMainRoom());
        log.info("Sottoscrizione ScoreListener completata");
        context.getEventManager().subscribe(new ScoreListener());
        log.info("DefaultGameInitializerDebug: inizializzazione completata");
    }
}
