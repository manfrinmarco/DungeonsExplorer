package manfrinmarco.io;

import java.util.Scanner;

public class InputSanitizer {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readInput() {
        String input = scanner.nextLine();
        return input.trim().toLowerCase();
    }
}
