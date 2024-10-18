package com.reflectionsoftware.service;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


// Serviço responsável por analisar classes compiladas usando Reflection
public class ReflectionService {

    public ReflectionService() {
        
    }

    /**
     * Analisa as classes compiladas de um diretório utilizando Reflection.
     * 
     * @param compiledClassFiles Arquivos compilados (.class) a serem analisados.
     * @param outputDir Diretório de saída onde os arquivos compilados estão localizados.
     * @return Lista de resultados da análise contendo informações sobre classes, métodos, etc.
     * @throws Exception Caso ocorra algum erro durante o carregamento e análise das classes.
     */
    public List<Class<?>> getClasses(List<File> compiledClassFiles, File outputDir) throws Exception {
        List<Class<?>> classes = new ArrayList<>();

        // Carrega o diretório de saída (onde os arquivos compilados .class estão) no classpath
        URL[] urls = { outputDir.toURI().toURL() };
        try (URLClassLoader classLoader = new URLClassLoader(urls)) {

            for (File classFile : compiledClassFiles) {
                // Extrai o nome da classe a partir do nome do arquivo .class
                String className = extractClassName(classFile, outputDir);

                // Carrega a classe pelo nome usando o classLoader
                Class<?> clazz = classLoader.loadClass(className);
                classes.add(clazz);
            }
        }

        return classes;
    }

    /**
     * Extrai o nome da classe a partir do caminho do arquivo .class.
     * 
     * @param classFile O arquivo .class a ser analisado.
     * @param outputDir O diretório de onde o arquivo foi compilado.
     * @return O nome da classe (com o pacote, se houver).
     */
    private String extractClassName(File classFile, File outputDir) {
        // Remove o diretório de saída do caminho completo e substitui barras por pontos
        String className = classFile.getAbsolutePath()
                .replace(outputDir.getAbsolutePath() + File.separator, "")
                .replace(".class", "")
                .replace(File.separator, ".");

        return className;
    }
}


