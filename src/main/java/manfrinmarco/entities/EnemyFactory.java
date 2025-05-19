// EnemyFactory.java — versione dinamica
package manfrinmarco.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.io.ReflectionLoader;

public class EnemyFactory {
    private static final Logger log = Logger.getLogger(EnemyFactory.class.getName());
    private static final Map<String, Enemy> registry = new HashMap<>();

    static {
        log.info("EnemyFactory: caricamento dinamico dei nemici custom");
        List<Object> loaded = ReflectionLoader.instantiateAnnotated("manfrinmarco.entities.custom");
        for (Object obj : loaded) {
            if (obj instanceof Enemy enemy) {
                String key = enemy.getName().toLowerCase();
                registry.put(key, enemy);
                log.log(Level.FINE, "EnemyFactory: registrato custom enemy: {0}", key);
            }
        }

        // fallback statici
        registry.put("goblin", new Enemy("Goblin", 30, new AggressiveStrategy()));
        log.fine("EnemyFactory: registrato fallback nemico: goblin");
        registry.put("skeleton", new Enemy("Scheletro", 25, new DefensiveStrategy()));
        log.fine("EnemyFactory: registrato fallback nemico: skeleton");
        registry.put("ratto", new Enemy("Ratto", 15, new AggressiveStrategy()));
        log.fine("EnemyFactory: registrato fallback nemico: ratto");
    }

    public static Enemy createEnemy(String type) {
        log.log(Level.FINE, "EnemyFactory.createEnemy: richiesta nemico di tipo ''{0}''", type);
        Enemy base = registry.get(type.toLowerCase());
        if (base == null) {
            log.log(Level.WARNING, "EnemyFactory: nemico non trovato: {0}", type);
            throw new IllegalArgumentException("Nemico non trovato: " + type);
        }
        log.fine("EnemyFactory.createEnemy: creato nemico: " + base.getName());
        return new Enemy(base.getName(), base.getHealth(), base.getStrategy());
    }
} 