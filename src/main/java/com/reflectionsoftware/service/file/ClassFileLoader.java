package com.reflectionsoftware.service.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ClassFileLoader {

    public static List<Class<?>> loadClasses(File rootDirectory, List<File> compiledClassFiles) {
        List<Class<?>> classes = new ArrayList<>();
        
        try {
            URL[] urls = { rootDirectory.toURI().toURL() };
            try (URLClassLoader classLoader = new URLClassLoader(urls)) {
                for (File classFile : compiledClassFiles) {
                    try {
                        String className = extractClassName(classFile, rootDirectory);
                        classes.add(classLoader.loadClass(className));
                    } catch (ClassNotFoundException e) {
                        System.err.println("Class not found for file: " + classFile.getAbsolutePath());
                        e.printStackTrace(); // Ou registre em um logger apropriado
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.err.println("URL inválida para `rootDirectory`: " + rootDirectory.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro para fechar o ClassLoader ou acessar os arquivos.");
            e.printStackTrace();
        } catch (SecurityException e) {
            System.err.println("Security exception ao acessar arquivos ou carregar as classes.");
            e.printStackTrace();
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
