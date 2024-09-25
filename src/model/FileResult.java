package model;

import java.io.File;

public class FileResult {
    
    private File javaFile;
    private CompilationResult compilationResult;
    private ReflectionResult reflectionResult;

    public FileResult(File javaFile) {
        this.javaFile = javaFile;
    }

    public void setCompilationResult(CompilationResult compilationResult) {
        if (compilationResult != null) {
            this.compilationResult = compilationResult;
        } else {
            throw new IllegalArgumentException("CompilationResult não deve ser nulo.");
        }
    }

    public void setReflectionResult(ReflectionResult reflectionResult) {
        this.reflectionResult = reflectionResult;
    }

    /**
     * Gera detalhes sobre a correção do arquivo .java.
     * 
     * @param index Index para identificar o número do arquivo.
     * @return String contendo detalhes da correção do arquivo .java.
     */
    public String generateFileDetails(int index) {

        String compilationResultString = compilationResult != null ? compilationResult.generateDetails() : "\t\tNenhum resultado disponível.";
        String reflectionResultString = reflectionResult != null ? reflectionResult.generateDetails() : "\t\tNenhum resultado disponível.";
        

        String details = String.format("""
        ----------------------------------------------
        Arquivo (%d): %s
        
        Resultado da Compilação:
        %s

        Resultado do Reflection:
        %s
        ----------------------------------------------
        """
            , index, javaFile.getName(), compilationResultString, reflectionResultString);
        return details;
    }
}
