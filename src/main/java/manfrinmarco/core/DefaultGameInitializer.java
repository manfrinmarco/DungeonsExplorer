package manfrinmarco.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.config.GameConfig;
import manfrinmarco.entities.DefensiveStrategy;
import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.EnemyFactory;
import manfrinmarco.entities.Player;
import manfrinmarco.events.DropListener;
import manfrinmarco.events.ScoreListener;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemType;
import manfrinmarco.map.CompositeRoom;
import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;

public class DefaultGameInitializer {
    private static final Logger log = Logger.getLogger(DefaultGameInitializer.class.getName());
    @SuppressWarnings("LoggerStringConcat")
    public static void initialize(GameContext context) {
        log.info("DefaultGameInitializerDebug: inizio inizializzazione");
        Player player = new Player("Eroe", Integer.parseInt(GameConfig.get("player.hp")));
        Inventory inventory = new Inventory();
        player.setInventory(inventory);
        context.setPlayer(player);
        log.fine("Giocatore creato: " + player.getName() + ", HP=" + player.getHealth());

        // stanze
        Room stanza1 = new Room("cucina ", "prima stanza");
        Room stanza2 = new Room("armeria", "seconda stanza");
        Room stanza3 = new Room("stanza del trono", "terza stanza");
        Room uscita = new Room("Uscita", "Hai vinto il gioco! Congratulazioni.");
        //uscite
        stanza1.setExit(Direction.NORTH, stanza2);
        stanza2.setExit(Direction.SOUTH, stanza1);
        stanza2.setExit(Direction.NORTH, stanza3);
        stanza3.setExit(Direction.SOUTH, stanza2);
        stanza3.setExit(Direction.NORTH, uscita);
        log.fine("Stanze create e collegate: stanza1, stanza2, stanza3, uscita");

        //potion
        Item pozione = new Item("Pozione", ItemType.POTION, 10);
        stanza1.addItem(pozione);
        Item pozione2 = new Item("Pozione", ItemType.POTION, 10);
        stanza1.addItem(pozione2);

        //chiave
        Item chiave = new Item("Chiave", ItemType.KEY, 0);
        uscita.setLocked(true, chiave);

        //nemici
        Item spada = new Item("Spada", ItemType.WEAPON, 50);
        Enemy nemico = EnemyFactory.createEnemy("Orco");
        stanza2.setEnemy(nemico);
        nemico.setDrop(spada);

        Enemy boss = new Enemy("Boss", 150, new DefensiveStrategy());
        boss.setDrop(chiave);
        stanza3.setEnemy(boss);

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
        context.getEventManager().subscribe(new DropListener());
        log.info("DefaultGameInitializerDebug: inizializzazione completata");
    }
}
