package com.reflectionsoftware.model.result.reflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe que representa o resultado da correção de uma etapa de correção.
 * Contém informações sobre o passo da correção e mensagens geradas para cada classe durante a correção.
 */
public class CorrectionResult {

    private String step; // Nome do passo da correção.
    
    // Mapas contendo o nome da classe e as mensagens de correção, separados por tipo (atributos, métodos, construtores).
    private Map<String, List<String>> attributesErrors;
    private Map<String, List<String>> attributesSuccesses;
    private Map<String, List<String>> methodsErrors;
    private Map<String, List<String>> methodsSuccesses;
    private Map<String, List<String>> constructorsErrors;
    private Map<String, List<String>> constructorsSuccesses;
    
    // Mapa de resumos (resultado geral da classe)
    private Map<String, String> resume;

    /**
     * Construtor que inicializa o passo da correção e os mapas de mensagens.
     * Inicialmente, os mapas estão vazios.
     *
     * @param step O nome do passo da correção.
     */
    public CorrectionResult(String step) {
        this.step = step;
        this.attributesErrors = new HashMap<>();
        this.attributesSuccesses = new HashMap<>();
        this.methodsErrors = new HashMap<>();
        this.methodsSuccesses = new HashMap<>();
        this.constructorsErrors = new HashMap<>();
        this.constructorsSuccesses = new HashMap<>();
        this.resume = new HashMap<>();
    }

    // Métodos para adicionar mensagens de sucesso e erro, agrupados por tipo (atributos, métodos, construtores)
    
    public void addAttributeSuccess(String className, String message) {
        this.attributesSuccesses.computeIfAbsent(className, k -> new ArrayList<>()).add(message);
    }

    public void addAttributeError(String className, String message) {
        this.attributesErrors.computeIfAbsent(className, k -> new ArrayList<>()).add(message);
    }

    public void addMethodSuccess(String className, String message) {
        this.methodsSuccesses.computeIfAbsent(className, k -> new ArrayList<>()).add(message);
    }

    public void addMethodError(String className, String message) {
        this.methodsErrors.computeIfAbsent(className, k -> new ArrayList<>()).add(message);
    }

    public void addConstructorSuccess(String className, String message) {
        this.constructorsSuccesses.computeIfAbsent(className, k -> new ArrayList<>()).add(message);
    }

    public void addConstructorError(String className, String message) {
        this.constructorsErrors.computeIfAbsent(className, k -> new ArrayList<>()).add(message);
    }

    // Adicionar resumo geral para a classe
    public void addResume(String className, String message) {
        this.resume.put(className, message);
    }

    // Métodos para retornar os mapas
    public Map<String, List<String>> getAttributesErrors() {
        return attributesErrors;
    }

    public Map<String, List<String>> getAttributesSuccesses() {
        return attributesSuccesses;
    }

    public Map<String, List<String>> getMethodsErrors() {
        return methodsErrors;
    }

    public Map<String, List<String>> getMethodsSuccesses() {
        return methodsSuccesses;
    }

    public Map<String, List<String>> getConstructorsErrors() {
        return constructorsErrors;
    }

    public Map<String, List<String>> getConstructorsSuccesses() {
        return constructorsSuccesses;
    }

    public Map<String, String> getResume() {
        return resume;
    }

    // Método que retorna o nome do passo
    public String getStep() {
        return step;
    }

    // Método que verifica se há algum erro geral (atributos, métodos ou construtores)
    public boolean hasSomeError() {
        return hasAttributeErrors() || hasMethodErrors() || hasConstructorErrors();
    }

    // Método que verifica se há erros de atributos
    public boolean hasAttributeErrors() {
        return !attributesErrors.isEmpty();
    }

    // Método que verifica se há erros de métodos
    public boolean hasMethodErrors() {
        return !methodsErrors.isEmpty();
    }

    // Método que verifica se há erros de construtores
    public boolean hasConstructorErrors() {
        return !constructorsErrors.isEmpty();
    }

    // Representação textual do resultado da correção
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultado da correção para o passo: ").append(step).append("\n");

        sb.append("Erros nos Atributos:\n");
        for (Map.Entry<String, List<String>> entry : attributesErrors.entrySet()) {
            sb.append("Classe: ").append(entry.getKey()).append("\n");
            for (String message : entry.getValue()) {
                sb.append("  - ").append(message).append("\n");
            }
        }

        sb.append("Sucessos nos Atributos:\n");
        for (Map.Entry<String, List<String>> entry : attributesSuccesses.entrySet()) {
            sb.append("Classe: ").append(entry.getKey()).append("\n");
            for (String message : entry.getValue()) {
                sb.append("  - ").append(message).append("\n");
            }
        }

        sb.append("Erros nos Métodos:\n");
        for (Map.Entry<String, List<String>> entry : methodsErrors.entrySet()) {
            sb.append("Classe: ").append(entry.getKey()).append("\n");
            for (String message : entry.getValue()) {
                sb.append("  - ").append(message).append("\n");
            }
        }

        sb.append("Sucessos nos Métodos:\n");
        for (Map.Entry<String, List<String>> entry : methodsSuccesses.entrySet()) {
            sb.append("Classe: ").append(entry.getKey()).append("\n");
            for (String message : entry.getValue()) {
                sb.append("  - ").append(message).append("\n");
            }
        }

        sb.append("Erros nos Construtores:\n");
        for (Map.Entry<String, List<String>> entry : constructorsErrors.entrySet()) {
            sb.append("Classe: ").append(entry.getKey()).append("\n");
            for (String message : entry.getValue()) {
                sb.append("  - ").append(message).append("\n");
            }
        }

        sb.append("Sucessos nos Construtores:\n");
        for (Map.Entry<String, List<String>> entry : constructorsSuccesses.entrySet()) {
            sb.append("Classe: ").append(entry.getKey()).append("\n");
            for (String message : entry.getValue()) {
                sb.append("  - ").append(message).append("\n");
            }
        }

        sb.append("Resumos:\n");
        for (Map.Entry<String, String> entry : resume.entrySet()) {
            sb.append("Classe: ").append(entry.getKey()).append("\n");
            sb.append("  - ").append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }
}
