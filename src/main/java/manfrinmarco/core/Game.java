package manfrinmarco.core;

import java.util.Scanner;

public class Game {
    private final GameContext context = GameContext.getInstance();
    private final CommandProcessor processor = new CommandProcessor();

    public void start() {
        DefaultGameInitializer.initialize(context);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Benvenuto in Dungeon Explorer!");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            processor.processCommand(input);
        }
    }
}