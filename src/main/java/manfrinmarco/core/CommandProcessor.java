package manfrinmarco.core;

import manfrinmarco.io.InputSanitizer;

public abstract class CommandProcessor {
    public final void processCommand(String input) {
        String sanitized = sanitizeInput(input);
        String[] tokens = parseCommand(sanitized);
        executeCommand(tokens);
    }

    protected String sanitizeInput(String input) {
        return InputSanitizer.clean(input);
    }

    protected String[] parseCommand(String input) {
        return input.trim().split("\\s+");
    }

    protected abstract void executeCommand(String[] tokens);
}