package com.reflectionsoftware.model.result;

import com.reflectionsoftware.model.result.compilation.CompilationResult;
import com.reflectionsoftware.model.result.reflection.ReflectionResult;

/**
 * Classe que representa o resultado de operações de compilação e reflexão.
 * Contém os resultados da compilação de código e da análise de classes via reflexão.
 */
public class Result {
    private CompilationResult compilationResult; // Resultado da compilação
    private ReflectionResult reflectionResult;   // Resultado da análise por reflexão

    /**
     * Construtor para inicializar os resultados de compilação e reflexão.
     *
     * @param compilationResult Resultado da compilação.
     * @param reflectionResult Resultado da análise por reflexão.
     */
    public Result(CompilationResult compilationResult, ReflectionResult reflectionResult) {
        this.compilationResult = compilationResult; // Inicializa o resultado da compilação
        this.reflectionResult = reflectionResult;     // Inicializa o resultado da reflexão
    }

    /**
     * Retorna o resultado da compilação.
     *
     * @return Resultado da compilação.
     */
    public CompilationResult getCompilationResult() {
        return compilationResult; // Retorna o resultado da compilação
    }

    /**
     * Retorna o resultado da análise por reflexão.
     *
     * @return Resultado da análise por reflexão.
     */
    public ReflectionResult getReflectionResult() {
        return reflectionResult; // Retorna o resultado da reflexão
    }
}
