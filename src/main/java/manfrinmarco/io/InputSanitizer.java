package manfrinmarco.io;

public class InputSanitizer {
    public static String clean(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\s]", "");
    }
}