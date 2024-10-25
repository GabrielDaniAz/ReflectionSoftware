package com.reflectionsoftware.model.result.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.reflectionsoftware.controller.CriteriaManager;
import com.reflectionsoftware.model.criteria.CriteriaByClass;
import com.reflectionsoftware.model.criteria.CriteriaCorrection;
import com.reflectionsoftware.model.criteria.GeneralCriteria;

/**
 * Classe que representa o resultado da análise de várias classes usando reflexão.
 * Armazena uma lista de análises realizadas em cada classe.
 */
public class ReflectionResult {

    private List<ReflectionClassAnalyses> reflectionClassAnalyses; // Lista de análises de classes.
    private List<CorrectionResult> correctionResults;

    /**
     * Construtor que inicializa a classe e suas análises.
     *
     * @param classes Lista de classes a serem analisadas.
     */
    public ReflectionResult(List<Class<?>> classes) {
        // Inicializa a lista de análises usando streams
        this.reflectionClassAnalyses = classes.stream()
            .map(ReflectionClassAnalyses::new) // Mapeia cada classe para uma nova análise
            .collect(Collectors.toList()); // Coleta os resultados em uma lista
        correctionResults = correct();
    }

    /**
     * Método que aplica critérios de correção às análises das classes.
     * Gera um resultado detalhado da correção.
     *
     * @return Lista de resultados de correção para cada classe analisada.
     */
    private List<CorrectionResult> correct() {
        CriteriaCorrection correctionCriteria = CriteriaManager.getCriteriaCorrection();
        GeneralCriteria generalCriteria = correctionCriteria.getGeneralCriteria();
        
        // Obtém o mapa de critérios por classe
        Map<String, List<CriteriaByClass>> criteriaMap = correctionCriteria.getCriteriaByClass();
        
        List<CorrectionResult> correctionResults = new ArrayList<>();

        // Para cada passo de correção (step), aplique os critérios
        for (Map.Entry<String, List<CriteriaByClass>> entry : criteriaMap.entrySet()) {
            String step = entry.getKey(); // Passo
            List<CriteriaByClass> criteriaList = entry.getValue(); // Lista de critérios

            CorrectionResult result = new CorrectionResult(step);

            System.out.println("Aplicando critérios para o passo: " + step); // Log para verificar o passo

            // Para cada critério da lista, aplicá-los às análises das classes
            for (CriteriaByClass criteria : criteriaList) {
                boolean classFound = false; // Variável para checar se a classe foi encontrada nas análises
                
                for (ReflectionClassAnalyses clazz : reflectionClassAnalyses) {
                    if (clazz.getName().equals(criteria.getClassName())) {
                        boolean isCorrect = clazz.applyCriteria(generalCriteria, criteria, result); // Passa o resultado de correção
                        
                        if (!isCorrect) {
                            result.addResume(clazz.getName(), "Classe " + clazz.getName() + " não está conforme os critérios.");
                        } else {
                            result.addResume(clazz.getName(), "Classe " + clazz.getName() + " está conforme os critérios.");
                        }
                        
                        classFound = true;
                        break; // Classe encontrada, sai do loop
                    }
                }

                if (!classFound) {
                    // Caso nenhuma classe tenha sido encontrada para o critério
                    result.addResume(criteria.getClassName(), "Classe " + criteria.getClassName() + " não foi encontrada.");
                }
            }

            correctionResults.add(result); // Adiciona o resultado da correção ao final de cada passo
        }

        return correctionResults; // Retorna a lista de resultados de correção
    }

    public List<CorrectionResult> getCorrectionResults(){
        return correctionResults;
    }
}
