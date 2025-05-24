package manfrinmarco.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.io.ReflectionLoader;
import manfrinmarco.security.GameException;

public class RoomFactory {
    private static final Logger log = Logger.getLogger(RoomFactory.class.getName());
    private static final Map<String, RoomTemplate> registry = new HashMap<>();
    private static boolean initialized = false;

    private static void initIfNeeded() {
        log.info("RoomFactory: inizializzazione dinamica dei template stanza");
        if (initialized) return;

        List<Object> templates = ReflectionLoader.instantiateAnnotated("manfrinmarco.map.custom");
        for (Object obj : templates) {
            if (obj instanceof RoomTemplate template) {
                registry.put(template.getType().toLowerCase(), template);
                log.log(Level.FINE, "RoomFactory: registrato template stanza: {0}", template.getType());
            }
        }
        initialized = true;
    }

    public static Room createRoom(String type) {
        log.log(Level.FINE, "RoomFactory.create: richiesta stanza tipo=''{0}''", type);
        initIfNeeded();
        RoomTemplate template = registry.get(type.toLowerCase());
        if (template == null) {
            log.log(Level.WARNING, "RoomFactory: tipo stanza non trovato: {0}", type);
            throw new GameException("Tipo stanza non trovato: " + type);
        }
        log.log(Level.INFO, "RoomFactory.create: creata stanza tipo: {0}", type);
        return template.create();
    }
}