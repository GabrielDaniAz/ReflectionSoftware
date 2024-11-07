package com.reflectionsoftware.service.compilation;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import com.reflectionsoftware.service.file.ClassFileLoader;

public class CompilationService {
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public static CompilationResult compileAndLoadClasses(File rootDirectory, List<File> javaFiles) throws Exception {
        if (compiler == null) {
            throw new IllegalStateException("Compilador Java não encontrado. Certifique-se de estar utilizando o JDK.");
        }

        File binDirectory = new File(rootDirectory, "bin");
        CompilationDiagnostic diagnostic = compileJavaFiles(binDirectory, javaFiles);

        if (diagnostic.hasErrors()) {
            return new CompilationResult(rootDirectory.getName(), List.of(), diagnostic);
        }

        List<Class<?>> classes = loadCompiledClasses(binDirectory, diagnostic.getCompiledFiles());
        return new CompilationResult(rootDirectory.getName(), classes, diagnostic);
    }

    private static CompilationDiagnostic compileJavaFiles(File compileDir, List<File> javaFiles) {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        
        if (javaFiles.isEmpty()) {
            System.out.println("Nenhum arquivo .java fornecido para compilação.");
            return new CompilationDiagnostic(false, diagnostics.getDiagnostics(), Collections.emptyList());
        }

        try (StandardJavaFileManager fileManager = createFileManager(compileDir, diagnostics)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
            boolean success = executeCompilation(compilationUnits, fileManager, diagnostics);
            List<File> compiledFiles = success ? getCompiledFilePaths(compileDir, javaFiles) : Collections.emptyList();
            return new CompilationDiagnostic(success, diagnostics.getDiagnostics(), compiledFiles);
        } catch (IOException e) {
            System.err.println("Erro ao gerenciar arquivos Java: " + e.getMessage());
            return new CompilationDiagnostic(false, diagnostics.getDiagnostics(), Collections.emptyList());
        }
    }

    private static List<Class<?>> loadCompiledClasses(File binDirectory, List<File> compiledFiles) throws Exception {
        return ClassFileLoader.loadClasses(binDirectory, compiledFiles);
    }

    private static StandardJavaFileManager createFileManager(File compileDir, DiagnosticCollector<JavaFileObject> diagnostics) throws IOException {
        if (!compileDir.exists() && !compileDir.mkdirs()) {
            throw new IOException("Não foi possível criar o diretório de compilação: " + compileDir.getPath());
        }
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.getDefault(), null);
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(compileDir));
        return fileManager;
    }

    private static boolean executeCompilation(Iterable<? extends JavaFileObject> compilationUnits, StandardJavaFileManager fileManager, DiagnosticCollector<JavaFileObject> diagnostics) {
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
        return task.call();
    }

    private static List<File> getCompiledFilePaths(File compileDir, List<File> javaFiles) {
        return javaFiles.stream()
                .map(javaFile -> new File(compileDir, javaFile.getName().replace(".java", ".class")))
                .collect(Collectors.toList());
    }

    public static class CompilationDiagnostic {
        private final boolean success;
        private final List<Diagnostic<? extends JavaFileObject>> diagnostics;
        private final List<File> compiledFiles;

        public CompilationDiagnostic(boolean success, List<Diagnostic<? extends JavaFileObject>> diagnostics, List<File> compiledFiles) {
            this.success = success;
            this.diagnostics = diagnostics;
            this.compiledFiles = compiledFiles;
        }

        public boolean hasErrors() { return !success; }
        public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {  return diagnostics; }
        public List<File> getCompiledFiles() { return compiledFiles; }
    }

    public static class CompilationResult {
        private final Exercise exercise;
        private final CompilationDiagnostic compilationDiagnostic;

        public CompilationResult(String directoryName, List<Class<?>> classes, CompilationDiagnostic compilationDiagnostic) {
            this.exercise = new Exercise(directoryName, classes);
            this.compilationDiagnostic = compilationDiagnostic;
        }

        public Exercise getExercise() { return exercise; }
        public CompilationDiagnostic getCompilationDiagnostic() { return compilationDiagnostic; }
    }

    public static class Exercise {
        private String step;
        private List<Class<?>> classes;

        public Exercise(String step, List<Class<?>> classes){
            this.step = step;
            this.classes = classes;
        }

        public String getStep(){ return step; }
        public List<Class<?>> getClasses(){ return classes; }
    }
}

