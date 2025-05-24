package manfrinmarco.core;

import manfrinmarco.io.InputSanitizer;

public abstract class AbstractCommandProcessor {
    public final void processCommand(String input) {
        String sanitized = sanitizeInput(input);
        String[] tokens = parseCommand(sanitized);
        executeCommand(tokens, input);
    }

    protected String sanitizeInput(String input) {
        return InputSanitizer.clean(input);
    }

    protected String[] parseCommand(String input) {
        return input.trim().split("\\s+");
    }

    protected abstract void executeCommand(String[] tokens, String line);
}