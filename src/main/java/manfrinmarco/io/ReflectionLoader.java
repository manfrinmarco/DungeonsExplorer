package manfrinmarco.io;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import manfrinmarco.annotations.AutoLoad;

public class ReflectionLoader {
    private static final Logger log = Logger.getLogger(ReflectionLoader.class.getName());
    public static List<Object> instantiateAnnotated(String packageName) {
        List<Object> instances = new ArrayList<>();
        log.log(Level.INFO, "ReflectionLoader: caricamento classi annotate in package {0}", packageName);
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
                        log.log(Level.FINE, "ReflectionLoader: istanziata classe {0}", className);
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | java.lang.reflect.InvocationTargetException | java.net.URISyntaxException e) {
            log.log(Level.SEVERE, "ReflectionLoader: errore caricamento dinamico: {0}", e.getMessage());
        }
        return instances;
    }
}