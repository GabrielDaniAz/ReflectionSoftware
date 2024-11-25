package com.reflectionsoftware.service.compilation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import com.reflectionsoftware.model.result.compilation.CompilationResult;

public class CompilationService {
    private static final File projectRoot = new File(System.getProperty("user.dir"));
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private static final List<File> classpathFiles = Arrays.asList(
        new File(projectRoot, "lib/ReflectionLibTCC-1.0.0.jar")  // JAR dentro da pasta lib
    );

    public static CompilationResult compileClasses(File rootDirectory, List<File> javaFiles) {
        if (compiler == null) {
            String errorMessage = "Compilador Java não encontrado. Certifique-se de estar utilizando o JDK.";
            System.err.println(errorMessage);
            return new CompilationResult(errorMessage);
        }

        File binDirectory = new File(rootDirectory, "bin");
        try {
            return compileJavaFiles(rootDirectory, binDirectory, javaFiles);
        } catch (IOException e) {
            String errorMessage = "Erro ao tentar compilar os arquivos: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            return new CompilationResult(errorMessage);
        }
    }

    private static CompilationResult compileJavaFiles(File rootDirectory, File compileDir, List<File> javaFiles) throws IOException {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        
        if (javaFiles.isEmpty()) {
            String errorMessage = "A lista `javaFiles` está vazia.";
            System.err.println(errorMessage);
            return new CompilationResult(errorMessage);
        }

        try (StandardJavaFileManager fileManager = createFileManager(compileDir, diagnostics)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
            boolean success = executeCompilation(compilationUnits, fileManager, diagnostics);
            List<File> compiledFiles = success ? getCompiledFilePaths(compileDir, javaFiles) : Collections.emptyList();
            return new CompilationResult(rootDirectory, compileDir, compiledFiles, diagnostics.getDiagnostics());
            
        } catch (IOException e) {
            String errorMessage = "Erro ao gerenciar arquivos Java: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            throw e;
        }
    }

    // private static StandardJavaFileManager createFileManager(File compileDir, DiagnosticCollector<JavaFileObject> diagnostics) throws IOException {
    //     if (!compileDir.exists() && !compileDir.mkdirs()) {
    //         String errorMessage = "Não foi possível criar o diretório de compilação: " + compileDir.getPath();
    //         System.err.println(errorMessage);
    //         throw new IOException(errorMessage);
    //     }
    //     StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.getDefault(), null);
    //     fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(compileDir));
    //     return fileManager;
    // }

    private static StandardJavaFileManager createFileManager(File compileDir, DiagnosticCollector<JavaFileObject> diagnostics) throws IOException {
        if (!compileDir.exists() && !compileDir.mkdirs()) {
            String errorMessage = "Não foi possível criar o diretório de compilação: " + compileDir.getPath();
            System.err.println(errorMessage);
            throw new IOException(errorMessage);
        }
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.getDefault(), null);
        
        // Adiciona os arquivos JAR ao classpath
        List<File> classpath = new ArrayList<>(classpathFiles);
        fileManager.setLocation(StandardLocation.CLASS_PATH, classpath);

        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(compileDir));
        return fileManager;
    }


    private static boolean executeCompilation(Iterable<? extends JavaFileObject> compilationUnits, StandardJavaFileManager fileManager, DiagnosticCollector<JavaFileObject> diagnostics) {
        try {
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            return task.call();
        } catch (Exception e) {
            String errorMessage = "Erro durante a execução da compilação: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            return false;
        }
    }

    private static List<File> getCompiledFilePaths(File compileDir, List<File> javaFiles) {
        return javaFiles.stream()
                .map(javaFile -> new File(compileDir, javaFile.getName().replace(".java", ".class")))
                .collect(Collectors.toList());
    }
}
