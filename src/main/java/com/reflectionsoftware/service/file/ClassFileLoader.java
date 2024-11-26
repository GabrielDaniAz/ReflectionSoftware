package com.reflectionsoftware.service.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class ClassFileLoader {

    public static List<Class<?>> loadClasses(File rootDirectory, List<File> compiledClassFiles, List<File> dependencieFiles) {
        List<Class<?>> classes = new ArrayList<>();
        List<URL> urls = new ArrayList<>();

        try {
            // Adicionar o diretório com as classes compiladas
            urls.add(rootDirectory.toURI().toURL());

            // Adicionar os arquivos JAR de dependências
            for (File dependencieFile : dependencieFiles) {
                urls.add(dependencieFile.toURI().toURL());
            }

            // Criar o ClassLoader com todas as URLs
            try (URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]), ClassLoader.getSystemClassLoader())) {

                // Carregar as classes compiladas do diretório de saída
                for (File classFile : compiledClassFiles) {
                    try {
                        String className = extractClassName(classFile, rootDirectory);
                        Class<?> clazz = classLoader.loadClass(className);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        System.err.println("Classe não encontrada para o arquivo: " + classFile.getAbsolutePath());
                        e.printStackTrace();
                    }
                }

                // Carregar as classes de arquivos JAR, mas sem adicionar ao retorno
                for (File jarFile : dependencieFiles) {
                    if (jarFile.getName().endsWith(".jar")) {
                        loadClassesFromJar(jarFile, classLoader); // Não adiciona ao retorno, apenas carrega
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.err.println("URL inválida para `rootDirectory` ou arquivos de dependências.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao fechar o ClassLoader ou acessar os arquivos.");
            e.printStackTrace();
        }

        return classes;
    }

    private static void loadClassesFromJar(File jarFile, URLClassLoader classLoader) {
        try (JarFile jar = new JarFile(jarFile)) {
            jar.stream()
                .filter(entry -> entry.getName().endsWith(".class")) // Filtra apenas as classes
                .forEach(entry -> {
                    try {
                        String className = entry.getName().replace("/", ".").replace(".class", "");
                        classLoader.loadClass(className);
                    } catch (ClassNotFoundException e) {
                        System.err.println("Classe não encontrada no JAR: " + entry.getName());
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo JAR: " + jarFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private static String extractClassName(File classFile, File rootDirectory) {
        return classFile.getAbsolutePath()
                .replace(rootDirectory.getAbsolutePath() + File.separator, "")
                .replace(".class", "")
                .replace(File.separator, ".");
    }
}
