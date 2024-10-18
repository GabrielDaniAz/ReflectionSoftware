package com.reflectionsoftware.service;

import javax.tools.*;

import com.reflectionsoftware.model.result.compilation.CompilationResult;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Serviço responsável por compilar arquivos Java fornecidos.
 * Utiliza a API Java Compiler e gerencia diagnósticos durante o processo de compilação.
 */
public class CompilationService {

    private final JavaCompiler compiler;

    /**
     * Construtor inicializa o compilador Java usando o ToolProvider.
     */
    public CompilationService() {
        this.compiler = ToolProvider.getSystemJavaCompiler();
    }

    /**
     * Compila uma lista de arquivos .java e armazena os arquivos compilados no diretório fornecido.
     *
     * @param javaFiles Lista de arquivos .java a serem compilados.
     * @param compileDir Diretório onde os arquivos compilados serão armazenados.
     * @return Um {@link CompilationResult} contendo o sucesso da compilação e possíveis diagnósticos.
     */
    public CompilationResult compile(List<File> javaFiles, File compileDir) {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        if (javaFiles.isEmpty()) {
            System.out.println("Nenhum arquivo .java fornecido.");
            return new CompilationResult(compileDir, false, List.of()); // Falha, sem diagnósticos
        }

        try (StandardJavaFileManager fileManager = createFileManager(compileDir, diagnostics)) {
            Iterable<? extends JavaFileObject> compilationUnits = getCompilationUnits(fileManager, javaFiles);
            boolean success = executeCompilation(fileManager, compilationUnits, diagnostics);
            return new CompilationResult(compileDir, success, diagnostics.getDiagnostics());
        } catch (IOException e) {
            System.out.println("Erro ao gerenciar arquivos Java: " + e.getMessage());
            return new CompilationResult(compileDir, false, diagnostics.getDiagnostics());
        }
    }

    /**
     * Cria e configura o {@link StandardJavaFileManager} para gerenciar arquivos de entrada e saída de compilação.
     * Define o diretório de saída da compilação.
     *
     * @param compileDir Diretório de saída para os arquivos compilados.
     * @param diagnostics Coletor de diagnósticos para armazenar erros e avisos.
     * @return Um {@link StandardJavaFileManager} configurado.
     * @throws IOException Se houver erros ao configurar o gerenciador de arquivos.
     */
    private StandardJavaFileManager createFileManager(File compileDir, DiagnosticCollector<JavaFileObject> diagnostics) throws IOException {
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.getDefault(), null);
        if (!compileDir.exists()) {
            compileDir.mkdirs();
        }
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(compileDir));
        return fileManager;
    }

    /**
     * Converte uma lista de arquivos .java em unidades de compilação ({@link JavaFileObject}).
     *
     * @param fileManager Gerenciador de arquivos usado para criar objetos de arquivos de compilação.
     * @param javaFiles Lista de arquivos .java a serem compilados.
     * @return Um iterable de {@link JavaFileObject} correspondente aos arquivos de entrada.
     */
    private Iterable<? extends JavaFileObject> getCompilationUnits(StandardJavaFileManager fileManager, List<File> javaFiles) {
        return fileManager.getJavaFileObjectsFromFiles(javaFiles);
    }

    /**
     * Executa a tarefa de compilação dos arquivos fornecidos.
     *
     * @param fileManager Gerenciador de arquivos usado para a tarefa de compilação.
     * @param compilationUnits Unidades de compilação a serem processadas.
     * @param diagnostics Coletor de diagnósticos que armazenará erros e avisos.
     * @return {@code true} se a compilação for bem-sucedida, {@code false} caso contrário.
     */
    private boolean executeCompilation(StandardJavaFileManager fileManager, Iterable<? extends JavaFileObject> compilationUnits, DiagnosticCollector<JavaFileObject> diagnostics) {
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,           // Writer para mensagens de erro (null usa o padrão System.err)
                fileManager,        // Gerenciador de arquivos
                diagnostics,        // Coletor de diagnósticos (erros/warnings)
                null,       // Opções de compilação (nenhuma específica)
                null,       // Nomes de classes (nenhum específico)
                compilationUnits    // Arquivos a serem compilados
        );

        return task.call();
    }
}
