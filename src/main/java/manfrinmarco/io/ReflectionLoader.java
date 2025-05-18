package manfrinmarco.io;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import manfrinmarco.annotations.AutoLoad;

public class ReflectionLoader {
    public static List<Class<?>> findAnnotatedClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            String path = packageName.replace('.', '/');
            URL root = Thread.currentThread().getContextClassLoader().getResource(path);
            if (root == null) return classes;

            File[] files = new File(root.toURI()).listFiles();
            if (files == null) return classes;

            for (File file : files) {
                if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().replace(".class", "");
                    Class<?> cls = Class.forName(className);
                    if (cls.isAnnotationPresent(AutoLoad.class)) {
                        classes.add(cls);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Errore reflection: " + e.getMessage());
        }
        return classes;
    }
}