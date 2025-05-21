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
    private static final Map<String, Room> registry = new HashMap<>();

    static {
        log.info("RoomFactory: caricamento dinamico delle stanze custom");
        List<Object> loaded = ReflectionLoader.instantiateAnnotated("manfrinmarco.map.custom");
        for (Object obj : loaded) {
            if (obj instanceof Room room) {
                String key = room.getId().toLowerCase();
                registry.put(key, room);
                log.log(Level.FINE, "RoomFactory: registrata custom room: {0}", key);
            }
        }

        // fallback statici
        registry.put("corridoio", new Room("corridoio", "corridoio", "Un lungo corridoio buio."));
        registry.put("cella", new Room("cella","cella", "Una piccola cella abbandonata."));
        registry.put("entrata", new Room("entrata","entrata", "L'ingresso del castello."));
        registry.put("sala", new Room("sala", "sala", "La grande sala del trono."));
        registry.put("armeria", new Room("armeria", "armeria", "Contiene armi abbandonate."));
        registry.put("cripta", new Room("cripta", "cripta", "Una cripta buia e fredda."));
        registry.put("default", new Room("default", "default", "Stanza misteriosa."));
    }

    public static Room createRoom(String type) {
        log.log(Level.FINE, "RoomFactory.createRoom: richiesta stanza di tipo ''{0}''", type);
        Room base = registry.get(type.toLowerCase());
        if (base == null) {
            log.log(Level.WARNING, "RoomFactory: stanza non trovata: {0}", type);
            throw new GameException("Stanza non trovata: " + type);
        }
        log.log(Level.FINE, "RoomFactory.createRoom: creata stanza: {0}", base.getId());
        return new Room(base.getId(), base.getName(), base.getDescription());
    }
}