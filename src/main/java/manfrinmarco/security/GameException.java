package manfrinmarco.security;

public class GameException extends Exception {
    private final String userMessage;

    public GameException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
