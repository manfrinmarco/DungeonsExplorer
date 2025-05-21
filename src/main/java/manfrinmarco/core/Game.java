package manfrinmarco.core;

import java.util.Scanner;
import java.util.logging.Logger;

import manfrinmarco.events.DropListener;
import manfrinmarco.events.ScoreListener;
import manfrinmarco.io.GameFileManager;
import manfrinmarco.map.CompositeRoom;
import manfrinmarco.map.MapLoader;

public class Game {
    private final GameContext context = GameContext.getInstance();
    private final CommandProcessor processor = new CommandProcessor();

    public void start() {
        GameLogger.configure();
        Logger logger = Logger.getLogger(Game.class.getName());
        logger.info("Avvio del gioco");

        System.out.println("Benvenuto in Dungeon Explorer!");

        try (Scanner scanner = new Scanner(System.in)) {
            // scegli se iniziare nuova partita o caricare
            System.out.println("Nuova partita (a) o carica partita esistente (b)?");
            String choice = scanner.nextLine().trim();
            if ("b".equalsIgnoreCase(choice)) {
                GameStateMemento m = GameFileManager.loadMemento();
                if (m != null) {
                    context.copyFrom(m.getSnapshot());
                    context.getEventManager().subscribe(new ScoreListener());
                    context.getEventManager().subscribe(new DropListener());
                    System.out.println("Partita caricata, inizia il gioco");
                } else {
                    System.out.println("Nessuna salvataggio trovato, avvio una nuova partita.");
                    DefaultGameInitializerDebug.initialize(context);
                    System.out.println("Scegli la mappa (invio per demo):");
                    String mapFile = scanner.nextLine().trim();
                    String name = mapFile + ".json";
                    System.out.println(name);
                    if (!mapFile.isEmpty()) {
                        try {
                            CompositeRoom map = MapLoader.load(name);
                            context.setCurrentRoom(map.getMainRoom());
                            System.err.println("Mappa caricata, inizia il gioco");
                        } catch (Exception e) {
                            System.err.println("Errore nel caricamento mappa, uso la demo.");
                        }
                    }
                }
            } else {
                DefaultGameInitializerDebug.initialize(context);
                System.out.println("Scegli la mappa (invio per demo):");
                String mapFile = scanner.nextLine().trim();
                String name = mapFile + ".json";
                if (!mapFile.isEmpty()) {
                    try {
                        CompositeRoom map = MapLoader.load(name);
                        context.setCurrentRoom(map.getMainRoom());
                        System.err.println("Mappa caricata, inizia il gioco");
                    } catch (Exception e) {
                        System.err.println("Errore nel caricamento mappa, uso la demo.");
                    }
                }
            }
            
            while (true) {
                System.out.print("\n> ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) break;
                processor.processCommand(input);
            }
        }
    }
}
