package manfrinmarco.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manfrinmarco.io.ReflectionLoader;

public class ItemFactory {
    private static final Map<String, ItemTemplate> registry = new HashMap<>();

    static {
        List<Object> templates = ReflectionLoader.instantiateAnnotated("manfrinmarco.items.templates");
        for (Object obj : templates) {
            if (obj instanceof ItemTemplate template) {
                registry.put(template.getId().toLowerCase(), template);
                System.out.println("Item registrato: " + template.getId());
            }
        }
    }

    public static Item create(String id) {
        ItemTemplate template = registry.get(id.toLowerCase());
        if (template == null) throw new IllegalArgumentException("Item non trovato: " + id);
        return template.create();
    }
}