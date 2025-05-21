package manfrinmarco.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.io.ReflectionLoader;
import manfrinmarco.security.GameException;
public class ItemFactory {
    private static final Logger log = Logger.getLogger(ItemFactory.class.getName());
    private static final Map<String, ItemTemplate> registry = new HashMap<>();
    private static boolean initialized = false;

    private static void initIfNeeded() {
        log.info("ItemFactory: inizializzazione dinamica dei template");
        if (initialized) return;
        List<Object> templates = ReflectionLoader.instantiateAnnotated("manfrinmarco.items.custom");
        for (Object obj : templates) {
            if (obj instanceof ItemTemplate template) {
                registry.put(template.getName().toLowerCase(), template);
                log.log(Level.FINE, "ItemFactory: registrato template item: {0}", template.getName());
            }
        }
        initialized = true;
    }

    public static Item create(String id) {
        log.log(Level.FINE, "ItemFactory.create: richiesta item id=''{0}''", id);
        initIfNeeded();
        ItemTemplate template = registry.get(id.toLowerCase());
        if (template == null) {
            log.log(Level.WARNING, "ItemFactory: item non trovato: {0}", id);
            throw new GameException("Item non trovato: " + id);
        }
        log.log(Level.INFO, "ItemFactory.create: creato item: {0}", id);
        return template.create();
    }
}