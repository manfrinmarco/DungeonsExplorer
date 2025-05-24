package manfrinmarco.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.EnemyFactory;
import manfrinmarco.entities.Player;
import manfrinmarco.events.DropListener;
import manfrinmarco.events.ScoreListener;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemFactory;
import manfrinmarco.map.CompositeRoom;
import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;
import manfrinmarco.map.RoomFactory;

public class DefaultGameInitializer {
    private static final Logger log = Logger.getLogger(DefaultGameInitializer.class.getName());
    @SuppressWarnings("LoggerStringConcat")
    public static void initialize(GameContext context) {

        log.info("DefaultGameInitializerDebug: INIT");
        //player
        Player player = new Player("Eroe");
        context.setPlayer(player);
        log.fine("Giocatore creato: " + player.getName() + ", HP=" + player.getHealth());

        // stanze
        Room stanza1 = RoomFactory.createRoom("corridoio");
        Room stanza2 = RoomFactory.createRoom("armeria");
        Room stanza3 = RoomFactory.createRoom("bossRoom");
        Room uscita = RoomFactory.createRoom("uscita");

        stanza1.setExit(Direction.NORTH, stanza2);
        stanza2.setExit(Direction.SOUTH, stanza1);
        stanza2.setExit(Direction.NORTH, stanza3);
        stanza3.setExit(Direction.SOUTH, stanza2);
        stanza3.setExit(Direction.NORTH, uscita);

        log.fine("Stanze create e collegate: stanza1, stanza2, stanza3, uscita");

        // items
        Item chiave = ItemFactory.createItem("chiave");
        uscita.setLocked(true, chiave);
        Item spada = ItemFactory.createItem("spada");

        //enemyies
        Enemy nemico = EnemyFactory.createEnemy("Scheletro");
        stanza2.setEnemy(nemico);
        nemico.setDrop(spada);
        Enemy boss = EnemyFactory.createEnemy("Spettro");
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
        context.getEventManager().subscribe(new ScoreListener());
        log.info("Sottoscrizione ScoreListener completata");
        context.getEventManager().subscribe(new DropListener());
        log.info("Sottoscrizione ScoreListener completata");

        log.info("DefaultGameInitializerDebug: END");
    }
}
