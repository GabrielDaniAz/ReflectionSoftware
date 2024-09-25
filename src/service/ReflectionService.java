package service;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import model.ReflectionResult;

// Utiliza a biblioteca java.lang.reflect para inspecionar os arquivos .java, verificando classes, métodos e atributos.
public class ReflectionService {
    
    /**
     * Analisa um arquivo compilado .class e extrai informações usando reflexão.
     * 
     * @param javaFile Arquivo .java a ser analisado
     * @return Resultado da análise encapsulado em um ReflectionResult
     * @throws Exception 
     */
    public static ReflectionResult analyzeFile(File javaFile) throws Exception {

        Class<?> clazz = loadClass(javaFile);
        return new ReflectionResult(clazz);
    }
    
    /**
     * Carrega uma classe compilada a partir de um arquivo .java.
     * 
     * @param javaFile Arquivo .java a ser compilado e carregado.
     * @return Classe carregada via reflexão.
     * @throws Exception Se houver falha ao compilar ou carregar a classe.  
     */
    private static Class<?> loadClass(File javaFile) throws Exception {
        String classFilePath = javaFile.getAbsolutePath().replace(".java", ".class");
        File classFile = new File(classFilePath);
        URL classUrl = classFile.getParentFile().toURI().toURL();
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{classUrl})) {
            String className = javaFile.getName().replace(".java", "");
            return classLoader.loadClass(className);
        }
    }
}
