package manfrinmarco.events;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameEvent {
    private static final Logger log = Logger.getLogger(GameEvent.class.getName());
    private final String type;
    private final String message;

    public GameEvent(String type, String message) {
        log.log(Level.FINE, "GameEvent creato: type={0}, message={1}", new Object[]{type, message});
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
