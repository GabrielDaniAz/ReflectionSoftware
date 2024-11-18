package com.reflectionsoftware.service.compilation;

import java.io.File;
import java.io.IOException;
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
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public static CompilationResult compileAndLoadClasses(File rootDirectory, List<File> javaFiles) throws Exception {
        if (compiler == null) {
            throw new IllegalStateException("Compilador Java não encontrado. Certifique-se de estar utilizando o JDK.");
        }

        File binDirectory = new File(rootDirectory, "bin");
        return compileJavaFiles(rootDirectory, binDirectory, javaFiles);
    }

    private static CompilationResult compileJavaFiles(File rootDirectory, File compileDir, List<File> javaFiles) {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        
        if (javaFiles.isEmpty()) {
            return new CompilationResult("Lista `javaFiles` vazia");
        }

        try (StandardJavaFileManager fileManager = createFileManager(compileDir, diagnostics)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
            boolean success = executeCompilation(compilationUnits, fileManager, diagnostics);
            List<File> compiledFiles = success ? getCompiledFilePaths(compileDir, javaFiles) : Collections.emptyList();
            return new CompilationResult(rootDirectory, compileDir, compiledFiles, diagnostics.getDiagnostics());
            
        } catch (IOException e) {
            return new CompilationResult("Erro ao gerenciar arquivos Java: " + e.getMessage());
        }
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
}

