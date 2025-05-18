package manfrinmarco.core;

import java.util.Scanner;

public class Game {
    private final GameContext context = GameContext.getInstance();
    private final CommandProcessor processor = new CommandProcessor() {
        @Override
        protected void executeCommand(String[] tokens) {
            System.out.println("Comando eseguito: " + String.join(" ", tokens));
        }
    };

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Benvenuto in Dungeon Explorer!");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            processor.processCommand(input);
        }
    }
}