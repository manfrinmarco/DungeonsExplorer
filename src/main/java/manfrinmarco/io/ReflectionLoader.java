package manfrinmarco.io;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import manfrinmarco.annotations.AutoLoad;

public class ReflectionLoader {
    public static List<Object> instantiateAnnotated(String packageName) {
    List<Object> instances = new ArrayList<>();
    try {
        String path = packageName.replace('.', '/');
        URL root = Thread.currentThread().getContextClassLoader().getResource(path);
        if (root == null) return instances;
        File[] files = new File(root.toURI()).listFiles();
        if (files == null) return instances;

        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                Class<?> cls = Class.forName(className);
                if (cls.isAnnotationPresent(AutoLoad.class)) {
                    Object obj = cls.getDeclaredConstructor().newInstance();
                    instances.add(obj);
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Errore durante il caricamento dinamico: " + e.getMessage());
    }
    return instances;
}
}