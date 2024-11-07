package com.reflectionsoftware.service.file;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ClassFileLoader {
       
    public static List<Class<?>> loadClasses(File rootDirectory, List<File> compiledClassFiles) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        URL[] urls = { rootDirectory.toURI().toURL() };

        try (URLClassLoader classLoader = new URLClassLoader(urls)) {
            for (File classFile : compiledClassFiles) {
                String className = extractClassName(classFile, rootDirectory);
                classes.add(classLoader.loadClass(className));
            }
        }

        return classes;
    }

    private static String extractClassName(File classFile, File rootDirectory) {
        return classFile.getAbsolutePath()
                .replace(rootDirectory.getAbsolutePath() + File.separator, "")
                .replace(".class", "")
                .replace(File.separator, ".");
    }
}
