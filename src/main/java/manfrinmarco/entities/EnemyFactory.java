package manfrinmarco.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.io.ReflectionLoader;
import manfrinmarco.security.GameException;

public class EnemyFactory {
    private static final Logger log = Logger.getLogger(EnemyFactory.class.getName());
    private static final Map<String, EnemyTemplate> registry = new HashMap<>();
    private static boolean initialized = false;

    private static void initIfNeeded() {
        log.info("EnemyFactory: inizializzazione dinamica dei template nemici");
        if (initialized) return;
        List<Object> templates = ReflectionLoader.instantiateAnnotated("manfrinmarco.entities.custom");
        for (Object obj : templates) {
            if (obj instanceof EnemyTemplate template) {
                String key = template.getName().toLowerCase();
                registry.put(key, template);
                log.log(Level.FINE, "EnemyFactory: registrato template nemico: {0}", key);
            }
        }
        initialized = true;
    }

    public static Enemy createEnemy(String type) {
        log.log(Level.FINE, "EnemyFactory.createEnemy: richiesta nemico di tipo ''{0}''", type);
        initIfNeeded();
        EnemyTemplate template = registry.get(type.toLowerCase());
        if (template == null) {
            log.log(Level.WARNING, "EnemyFactory: nemico non trovato: {0}", type);
            throw new GameException("Nemico non trovato: " + type);
        }
        Enemy enemy = template.create();
        log.log(Level.INFO, "EnemyFactory.createEnemy: creato nemico: {0}", enemy.getName());
        return enemy;
    }
}