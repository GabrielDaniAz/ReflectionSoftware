package com.reflectionsoftware.model.result.compilation;

import java.io.File;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class CompilationResult {
    private final File rootDirectory;
    private final File compilationDirectory;
    private final List<File> compiledFiles;
    private final List<Diagnostic<? extends JavaFileObject>> diagnostics;
    private final List<File> jarFiles;
    private final String errorDetails;

    public CompilationResult(File rootDirectory, File compilationDirectory, List<File> compiledFiles, List<Diagnostic<? extends JavaFileObject>> diagnostics, List<File> jarFiles) {
        this.rootDirectory = rootDirectory;
        this.compilationDirectory = compilationDirectory;
        this.diagnostics = diagnostics;
        this.compiledFiles = compiledFiles;
        this.jarFiles = jarFiles;
        errorDetails = null;
    }

    public CompilationResult(String errorDetails){
        this.errorDetails = errorDetails;
        rootDirectory = null;
        compilationDirectory = null;
        diagnostics = null;
        compiledFiles = null;
        jarFiles = null;
    }

    public File getRootDirectory(){ return rootDirectory; }
    public File getCompilationDirectory(){ return compilationDirectory; }
    public boolean isSuccess() { return !compiledFiles.isEmpty() ? true : false; }
    public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {  return diagnostics; }
    public List<File> getCompiledFiles() { return compiledFiles; }
    public List<File> getJarFiles(){ return jarFiles; }
    public String getErrorDetails(){ return errorDetails; }
}