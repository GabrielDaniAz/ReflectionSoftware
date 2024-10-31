package com.reflectionsoftware.service;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ClassFileLoader {
       
    public static List<Class<?>> loadClasses(List<File> compiledClassFiles, File outputDir) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        URL[] urls = { outputDir.toURI().toURL() };

        try (URLClassLoader classLoader = new URLClassLoader(urls)) {
            for (File classFile : compiledClassFiles) {
                String className = extractClassName(classFile, outputDir);
                classes.add(classLoader.loadClass(className));
            }
        }

        return classes;
    }

    private static String extractClassName(File classFile, File outputDir) {
        return classFile.getAbsolutePath()
                .replace(outputDir.getAbsolutePath() + File.separator, "")
                .replace(".class", "")
                .replace(File.separator, ".");
    }
}
