package manfrinmarco.core;

import java.util.Scanner;

import manfrinmarco.events.ScoreListener;
import manfrinmarco.io.GameFileManager;
import manfrinmarco.map.CompositeRoom;
import manfrinmarco.map.MapLoader;

public class Game {
    private final GameContext context = GameContext.getInstance();
    private final CommandProcessor processor = new CommandProcessor();

    public void start() {


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
                    System.out.println("Partita caricata.");
                } else {
                    System.out.println("Nessuna partita salvata trovata, avvio nuova partita.");
                    DefaultGameInitializer.initialize(context);
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
                DefaultGameInitializer.initialize(context);
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

// 1. avvio del gioco -> benvenuto...
// 2. vuoi iniziare una nuova partita (a) o caricare una partita esistente (b)?
// 3. se (a) -> inizializza il gioco, se (b) -> carica partia salvata (.dat)
//4. se (a) -> segli la mappa da caricare (ad es piu json files in una cartella), se non scegli una mappa, 
//   o non esiste carica la mappa di default (defaultGameInitializer)