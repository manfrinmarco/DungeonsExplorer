package manfrinmarco.security;

public class GameException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String errorCode;

    public GameException(String message) {
        super(message);
        this.errorCode = null;
    }

    /** 
     * Creates a GameException with a specific error code.
     */
    public GameException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Creates a GameException with a cause.
     */
    public GameException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    /**
     * Creates a GameException with an error code and cause.
     */
    public GameException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        String prefix = (errorCode != null) ? "[" + errorCode + "] " : "";
        return prefix + getClass().getSimpleName() + ": " + getMessage();
    }
}