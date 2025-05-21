package manfrinmarco.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.config.GameConfig;
import manfrinmarco.entities.Enemy;
import manfrinmarco.entities.EnemyFactory;
import manfrinmarco.entities.Player;
import manfrinmarco.events.DropListener;
import manfrinmarco.events.ScoreListener;
import manfrinmarco.items.Inventory;
import manfrinmarco.items.Item;
import manfrinmarco.items.ItemFactory;
import manfrinmarco.map.CompositeRoom;
import manfrinmarco.map.Direction;
import manfrinmarco.map.Room;

public class DefaultGameInitializer {
    private static final Logger log = Logger.getLogger(DefaultGameInitializer.class.getName());

    public static void initialize(GameContext context) {
        log.info("Inizializzazione del gioco predefinito");
        Player player = new Player("Eroe", GameConfig.getInt("player.hp"));
        Inventory inventory = new Inventory();
        player.setInventory(inventory);
        context.setPlayer(player);
        log.log(Level.FINE, "Giocatore creato: {0} con HP={1}", new Object[]{player.getName(), player.getHealth()});

        // Zona 1: Costa
        Room nave = new Room("Nave", "Una vecchia nave attraccata.");
        Room porto = new Room("Porto", "Un porto di legno, bagnato dalle onde.");
        nave.setExit(Direction.NORTH, porto);
        porto.setExit(Direction.SOUTH, nave);
        Item ascia = ItemFactory.create("Ascia");
        porto.addItem(ascia);
        log.fine("Zona 'Costa' configurata con stanze Nave e Porto");

        // Zona 2: Giardino
        Room giardino = new Room("Giardino", "Un giardino pieno di vegetazione.");
        porto.setExit(Direction.NORTH, giardino);
        giardino.setExit(Direction.SOUTH, porto);
        Item elsa = ItemFactory.create("Elsa");
        giardino.addItem(elsa);

        Enemy giardinoEnemy = EnemyFactory.createEnemy("Demone");
        Item torcia = ItemFactory.create("Torcia");
        giardinoEnemy.setDrop(torcia);
        giardino.setEnemy(giardinoEnemy);
        log.log(Level.FINE, "Zona ''Giardino'' configurata con nemico: {0}", giardinoEnemy.getName());

        // Zona 3: Castello
        Room entrata = new Room("Entrata", "Una stanza buia, serve una torcia per esplorare.");
        giardino.setExit(Direction.NORTH, entrata);
        entrata.setExit(Direction.SOUTH, giardino);

        Room guardie = new Room("Stanza delle Guardie", "Una stanza sorvegliata da un nemico.");
        Room armeria = new Room("Armeria", "Contiene armi e armature abbandonate.");
        Room cucina = new Room("Cucina", "Odore di stufato e tre pozioni dimenticate.");
        Room boss = new Room("Stanza del Boss", "Un potente nemico ti aspetta qui.");
        Room uscita = new Room("Uscita", "Hai vinto il gioco! Congratulazioni.");

        entrata.setExit(Direction.NORTH, armeria);
        armeria.setExit(Direction.SOUTH, entrata);

        guardie.setExit(Direction.SOUTH, entrata);
        entrata.setExit(Direction.NORTH, guardie);

        armeria.setExit(Direction.SOUTH, guardie);
        guardie.setExit(Direction.EAST, armeria);

        // Blocco accesso cucina con chiave
        Item chiave = ItemFactory.create("Chiave");
        cucina.setLocked(true, chiave);
        armeria.setExit(Direction.EAST, cucina);
        cucina.setExit(Direction.WEST, armeria);

        // Pozioni nella cucina
        Item pozione1 = ItemFactory.create("Pozione");
        Item pozione2 = ItemFactory.create("Pozione");
        Item pozione3 = ItemFactory.create("Pozione");
        cucina.addItem(pozione1);
        cucina.addItem(pozione2);
        cucina.addItem(pozione3);


        // Accesso boss e uscita
        cucina.setExit(Direction.SOUTH, boss);
        boss.setExit(Direction.NORTH, cucina);
        boss.setExit(Direction.EAST, uscita);

        // Drop torcia già previsto in giardino
        Enemy guardia = EnemyFactory.createEnemy("Scheletro");
        guardia.setDrop(chiave);
        guardie.setEnemy(guardia);

        Item lama = ItemFactory.create("Lama");
        Item armatura = ItemFactory.create("Armatura");
        armeria.addItem(lama);
        armeria.addItem(armatura);

        Enemy bossEnemy = EnemyFactory.createEnemy("Demone");
        boss.setEnemy(bossEnemy);

        // Composizione castello
        CompositeRoom castello = new CompositeRoom("Castello sull'Isola", "Un castello antico e misterioso.");
        castello.addRoom(entrata);
        castello.addRoom(guardie);
        castello.addRoom(armeria);
        castello.addRoom(cucina);
        castello.addRoom(boss);
        castello.addRoom(uscita);
        castello.setMainRoom(entrata);
        log.log(Level.INFO, "Castello configurato con stanza principale: {0}", entrata.getName());

        // Composizione isola
        CompositeRoom isola = new CompositeRoom("Isola", "Un luogo remoto e pericoloso.");
        isola.addRoom(nave);
        isola.addRoom(porto);
        isola.addRoom(giardino);
        isola.addRoom(castello);
        isola.setMainRoom(nave);
        log.log(Level.INFO, "Mappa ''Isola'' pronta. Punto di spawn: {0}", nave.getName());

        context.setCurrentRoom(isola.getMainRoom());
        log.fine("Sottoscrizione ScoreListener al GameContext");
        context.getEventManager().subscribe(new ScoreListener());
        context.getEventManager().subscribe(new DropListener());
        log.info("DefaultGameInitializer completato");
    }
}
