package com.reflectionsoftware.model.result.reflection;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o resultado da correção de uma classe de código.
 * Contém informações sobre o nome da classe e mensagens geradas durante a correção.
 */
public class CorrectionResult {

    private String className; // Nome da classe que está sendo corrigida.
    private List<String> messages; // Lista de mensagens geradas durante a correção.

    /**
     * Construtor que inicializa o nome da classe e a lista de mensagens.
     * Inicialmente, a lista de mensagens está vazia.
     *
     * @param className O nome da classe a ser corrigida.
     */
    public CorrectionResult(String className) {
        this.className = className;
        this.messages = new ArrayList<>();
    }

    /**
     * Adiciona uma mensagem de correção ao resultado.
     * 
     * @param message A mensagem de correção a ser adicionada.
     */
    public void addMessage(String message) {
        this.messages.add(message);
    }

    /**
     * Retorna o nome da classe que está sendo corrigida.
     *
     * @return O nome da classe.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Retorna todas as mensagens geradas durante a correção.
     *
     * @return Uma lista de mensagens de correção.
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * Formata o resultado da correção como uma String para exibição.
     *
     * @return Uma representação textual do resultado da correção.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultado da correção para a classe: ").append(className).append("\n");
        for (String message : messages) {
            sb.append(message).append("\n");
        }
        return sb.toString();
    }
}
