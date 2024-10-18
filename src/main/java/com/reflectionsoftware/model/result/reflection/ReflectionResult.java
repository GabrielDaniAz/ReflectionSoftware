package com.reflectionsoftware.model.result.reflection;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o resultado da análise de várias classes usando reflexão.
 * Armazena uma lista de análises realizadas em cada classe.
 */
public class ReflectionResult {

    private List<ReflectionClassAnalyses> reflectionClassAnalyses; // Lista de análises de classes.

    /**
     * Construtor que inicializa a classe e suas análises.
     *
     * @param classes Lista de classes a serem analisadas.
     */
    public ReflectionResult(List<Class<?>> classes) {
        this.reflectionClassAnalyses = new ArrayList<>(); // Inicializa a lista de análises
        initializeReflectionClassAnalyses(classes); // Popula a lista com análises de classe
    }

    /**
     * Inicializa a lista de análises de classe para cada classe fornecida.
     *
     * @param classes Lista de classes a serem analisadas.
     */
    private void initializeReflectionClassAnalyses(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            reflectionClassAnalyses.add(new ReflectionClassAnalyses(clazz)); // Adiciona a análise da classe à lista
        }
    }

    /**
     * Retorna a lista de análises de classes realizadas.
     *
     * @return Lista de objetos ReflectionClassAnalyses.
     */
    public List<ReflectionClassAnalyses> getReflectionClassAnalyses() {
        return reflectionClassAnalyses; // Retorna a lista de análises
    }
}
