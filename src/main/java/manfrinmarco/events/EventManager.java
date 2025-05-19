package manfrinmarco.events;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventManager {
    private static final Logger log = Logger.getLogger(EventManager.class.getName());
    private final List<EventListener> listeners = new ArrayList<>();

    public void subscribe(EventListener listener) {
        listeners.add(listener);
        log.log(Level.INFO, "EventManager: listener sottoscritto: {0}", listener.getClass().getName());
    }

    public void notify(GameEvent event) {
        log.log(Level.FINE, "EventManager: invio evento ''{0}'' a {1} listener", new Object[]{event.getType(), listeners.size()});
        for (EventListener listener : listeners) {
            log.log(Level.FINE, "EventManager: notifico listener: {0}", listener.getClass().getName());
            listener.onEvent(event);
        }
    }
}