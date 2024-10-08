package service;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

// Serviço responsável por analisar classes compiladas usando Reflection
public class ReflectionAnalysisService {

    /**
     * Analisa as classes compiladas de um diretório utilizando Reflection.
     * 
     * @param compiledClassFiles Arquivos compilados (.class) a serem analisados.
     * @param outputDir Diretório de saída onde os arquivos compilados estão localizados.
     * @return Lista de resultados da análise contendo informações sobre classes, métodos, etc.
     * @throws Exception Caso ocorra algum erro durante o carregamento e análise das classes.
     */
    public List<String> analyzeClasses(List<File> compiledClassFiles, File outputDir) throws Exception {
        List<String> analysisResults = new ArrayList<>();

        // Carrega o diretório de saída (onde os arquivos compilados .class estão) no classpath
        URL[] urls = { outputDir.toURI().toURL() };
        try (URLClassLoader classLoader = new URLClassLoader(urls)) {

            for (File classFile : compiledClassFiles) {
                // Extrai o nome da classe a partir do nome do arquivo .class
                String className = extractClassName(classFile, outputDir);

                // Carrega a classe pelo nome usando o classLoader
                Class<?> clazz = classLoader.loadClass(className);
                analysisResults.add(analyzeClass(clazz));
            }
        }

        return analysisResults;
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

    /**
     * Analisa uma classe individualmente utilizando Reflection e retorna informações sobre ela.
     * 
     * @param clazz A classe a ser analisada.
     * @return Um relatório em formato de string contendo detalhes da classe.
     */
    private String analyzeClass(Class<?> clazz) {
        StringBuilder result = new StringBuilder();
        result.append("Analisando a classe: ").append(clazz.getName()).append("\n");

        // Inspecionar os construtores da classe
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        result.append("Construtores:\n");
        for (Constructor<?> constructor : constructors) {
            result.append("\t").append(constructor.toString()).append("\n");
        }

        // Inspecionar os métodos da classe
        Method[] methods = clazz.getDeclaredMethods();
        result.append("Métodos:\n");
        for (Method method : methods) {
            result.append("\t").append(method.toString()).append("\n");
        }

        // Inspecionar os campos (atributos) da classe
        Field[] fields = clazz.getDeclaredFields();
        result.append("Campos (atributos):\n");
        for (Field field : fields) {
            result.append("\t").append(field.toString()).append("\n");
            int modifiers = field.getModifiers();
            result.append("\tModificador: ").append(Modifier.toString(modifiers)).append("\n");
        }

        result.append("\n");
        return result.toString();
    }
}



// package model;

// import java.util.Arrays;
// import java.lang.reflect.Constructor;
// import java.lang.reflect.Field;
// import java.lang.reflect.Method;

// // Modelo responsável por armazenar os resultados da análise reflection
// public class ReflectionResult {

//     private Class<?> clazz;
//     private Constructor<?>[] constructors;
//     private Field[] fields;
//     private Method[] methods;

//     // Construtor para inicializar a classe e seus membros
//     public ReflectionResult(Class<?> clazz) {
//         this.clazz = clazz;
//         this.constructors = clazz.getConstructors();
//         this.fields = clazz.getDeclaredFields();
//         this.methods = clazz.getDeclaredMethods();
//     }

//     // Gera um resumo dos detalhes da classe
//     public String generateDetails() {
//         String details = String.format("""
//         %s

//         %s

//         %s

//         %s
//         """, classInformation(), constructorsInformation(), fieldsInformation(), methodsInformation());

//         return details;
//     }

//     // Informações básicas da classe
//     private String classInformation() {
//         String superclassesString = clazz.getSuperclass() != null ? clazz.getSuperclass().getName() : "None";
//         String interfaceString = (clazz.getInterfaces().length > 0) ? Arrays.toString(clazz.getInterfaces()) : "None";
//         String declaredClassesString = (clazz.getDeclaredClasses().length > 0) ? Arrays.toString(clazz.getDeclaredClasses()) : "None";

//         return String.format("""
//             - Classe %s:
//                 > SuperClasse: %s,
//                 > Interface: %s,
//                 > Classes Declaradas: %s,
//                 > Quantidade de Construtores: %d,
//                 > Quantidade de Atributos: %d,        
//                 > Quantidade de Métodos: %d,
//         """,
//         clazz.getName(), superclassesString, interfaceString, declaredClassesString, constructors.length, fields.length, methods.length);
//     }

//     // Detalhes sobre os construtores
//     private String constructorsInformation() {
//         StringBuilder information = new StringBuilder(String.format("""
//             - Construtores: 
//                 > Quantidade de Construtores: %d,       
//         """, constructors.length));

//         int position = 1;
//         for (Constructor<?> constructor : constructors) {
//             String parameters = (constructor.getParameterTypes().length > 0) ? Arrays.toString(constructor.getParameterTypes()) : "None";
            
//             information.append(String.format("""
//                         - Construtor (%d):
//                             # Parâmetros: %s
//             """, position, parameters));

//             position++;
//         }
//         return information.toString();
//     }

//     // Detalhes sobre os métodos
//     private String methodsInformation() {
//         StringBuilder information = new StringBuilder(String.format("""
//             - Métodos: 
//                 > Quantidade de Métodos: %d,       
//         """, methods.length));

//         for (Method method : methods) {
//             String parameters = (method.getParameterTypes().length > 0) ? Arrays.toString(method.getParameterTypes()) : "None";

//             information.append(String.format("""
//                         - Método: %s,
//                             # Modificador: %d,
//                             # Quantidade de parâmetros: %d,
//                             # Parâmetros: %s,
//                             # Retorno: %s
//             """, 
//             method.getName(), method.getModifiers(), method.getParameterCount(), 
//             parameters, method.getReturnType().getSimpleName()));
//         }
//         return information.toString();
//     }

//     // Detalhes sobre os atributos
//     private String fieldsInformation() {
//         StringBuilder information = new StringBuilder(String.format("""
//             - Atributos: 
//                 > Quantidade de Atributos: %d,       
//         """, fields.length));

//         for (Field field : fields) {
//             information.append(String.format("""
//                         - Atributo: %s,
//                             # Modificador: %d,
//                             # Tipo: %s
//             """, field.getName(), field.getModifiers(), field.getType()));
//         }

//         return information.toString();
//     }
// }


