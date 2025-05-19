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
                    System.out.println("Partita caricata.");
                } else {
                    System.out.println("Nessuna partita salvata trovata, avvio nuova partita.");
                    DefaultGameInitializerDebug.initialize(context);
                    System.out.println("Scegli file mappa JSON (invio per default):");
                    String mapFile = scanner.nextLine().trim();
                    if (!mapFile.isEmpty()) {
                        try {
                            CompositeRoom map = MapLoader.load(mapFile);
                            context.setCurrentRoom(map.getMainRoom());
                        } catch (Exception e) {
                            System.err.println("Errore caricamento mappa, uso configurazione di default.");
                        }
                    }
                }
            } else {
                DefaultGameInitializerDebug.initialize(context);
                System.out.println("Scegli file mappa JSON (invio per default):");
                String mapFile = scanner.nextLine().trim();
                if (!mapFile.isEmpty()) {
                    try {
                        CompositeRoom map = MapLoader.load(mapFile);
                        context.setCurrentRoom(map.getMainRoom());
                    } catch (Exception e) {
                        System.err.println("Errore caricamento mappa, uso configurazione di default.");
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
