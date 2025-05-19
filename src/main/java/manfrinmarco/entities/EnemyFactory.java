// EnemyFactory.java — versione dinamica
package manfrinmarco.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manfrinmarco.io.ReflectionLoader;

public class EnemyFactory {
    private static final Map<String, Enemy> registry = new HashMap<>();

    static {
        List<Object> loaded = ReflectionLoader.instantiateAnnotated("manfrinmarco.entities.custom");
        for (Object obj : loaded) {
            if (obj instanceof Enemy enemy) {
                String key = enemy.getName().toLowerCase();
                registry.put(key, enemy);
                System.out.println("Enemy registrato: " + key);
            }
        }

        // fallback statici
        registry.put("goblin", new Enemy("Goblin", 30, new AggressiveStrategy()));
        registry.put("skeleton", new Enemy("Scheletro", 25, new DefensiveStrategy()));
        registry.put("ratto", new Enemy("Ratto", 15, new AggressiveStrategy()));
    }

    public static Enemy createEnemy(String type) {
        Enemy base = registry.get(type.toLowerCase());
        if (base == null) throw new IllegalArgumentException("Nemico non trovato: " + type);

        return new Enemy(base.getName(), base.getHealth(), base.getStrategy());
    }
} 