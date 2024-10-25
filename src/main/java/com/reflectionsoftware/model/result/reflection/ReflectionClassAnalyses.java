package com.reflectionsoftware.model.result.reflection;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

import com.reflectionsoftware.model.criteria.CriteriaByClass;
import com.reflectionsoftware.model.criteria.CriteriaConstructor;
import com.reflectionsoftware.model.criteria.CriteriaField;
import com.reflectionsoftware.model.criteria.CriteriaMethod;
import com.reflectionsoftware.model.criteria.GeneralCriteria;

/**
 * Classe que analisa uma classe Java usando reflexão.
 * Fornece métodos para carregar informações sobre a classe,
 * verificar critérios de correção e gerar resultados de correção.
 */
public class ReflectionClassAnalyses {

    private Class<?> clazz; // Classe a ser analisada.

    /**
     * Construtor que inicializa a análise da classe.
     *
     * @param clazz Classe a ser analisada.
     */
    public ReflectionClassAnalyses(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return clazz.getSimpleName();
    }

    /**
     * Executa a correção da classe de acordo com os critérios fornecidos.
     *
     * @param generalCriteria Os critérios gerais de correção.
     * @param criteria        Os critérios específicos da classe.
     * @param result          O resultado da correção, onde as mensagens serão armazenadas.
     * @return true se a classe estiver conforme os critérios, false caso contrário.
     */
    public boolean applyCriteria(GeneralCriteria generalCriteria, CriteriaByClass criteria, CorrectionResult result) {
        boolean isValid = true;

        isValid &= validateFields(criteria, result);
        isValid &= validateConstructors(criteria, result);
        isValid &= validateMethods(criteria, result);

        return isValid; // Retorna o resultado da validação
    }

    /**
     * Valida os atributos (fields) da classe de acordo com os critérios.
     *
     * @param criteria Critérios de correção para a classe.
     * @param result   O resultado da correção, onde as mensagens serão armazenadas.
     * @return true se os atributos estiverem corretos, false caso contrário.
     */
    private boolean validateFields(CriteriaByClass criteria, CorrectionResult result) {
        List<CriteriaField> expectedFields = criteria.getAttributes();
        boolean isValid = true;

        for (CriteriaField expectedField : expectedFields) {
            Field matchingField = findField(expectedField);
            if (matchingField == null) {
                result.addAttributeError(getName(), "Campo esperado não encontrado: " + expectedField.getName());
                isValid = false;
            } else {
                result.addAttributeSuccess(getName(), "Campo encontrado: " + expectedField.getName());
                if (expectedField.isFinal() && !Modifier.isFinal(matchingField.getModifiers())) {
                    result.addAttributeError(getName(), "Campo não é final: " + expectedField.getName());
                    isValid = false;
                } else if (expectedField.isFinal()) {
                    result.addAttributeSuccess(getName(), "Campo final validado: " + expectedField.getName());
                }
            }
        }

        return isValid;
    }

    private Field findField(CriteriaField expectedField) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(expectedField.getName()) && field.getType().getSimpleName().equals(expectedField.getType())) {
                return field; // Campo correspondente encontrado.
            }
        }
        return null; // Nenhum campo correspondente encontrado.
    }

    /**
     * Valida os construtores da classe de acordo com os critérios.
     *
     * @param criteria Critérios de correção para a classe.
     * @param result   O resultado da correção, onde as mensagens serão armazenadas.
     * @return true se os construtores estiverem corretos, false caso contrário.
     */
    private boolean validateConstructors(CriteriaByClass criteria, CorrectionResult result) {
        List<CriteriaConstructor> expectedConstructors = criteria.getConstructors();
        boolean isValid = true;

        for (CriteriaConstructor expectedConstructor : expectedConstructors) {
            Constructor<?> matchingConstructor = findConstructor(expectedConstructor);
            if (matchingConstructor == null) {
                result.addConstructorError(getName(), "Construtor esperado não encontrado com os parâmetros: " + expectedConstructor.getParameters());
                isValid = false;
            } else {
                result.addConstructorSuccess(getName(), "Construtor encontrado com os parâmetros: " + expectedConstructor.getParameters());
            }
        }

        return isValid;
    }

    private Constructor<?> findConstructor(CriteriaConstructor expectedConstructor) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (matchParameters(constructor.getParameterTypes(), expectedConstructor.getParameters())
                && matchVisibility(constructor.getModifiers(), expectedConstructor.getVisibility())) {
                return constructor; // Construtor correspondente encontrado.
            }
        }
        return null; // Nenhum construtor correspondente encontrado.
    }

    /**
     * Valida os métodos da classe de acordo com os critérios.
     *
     * @param criteria Critérios de correção para a classe.
     * @param result   O resultado da correção, onde as mensagens serão armazenadas.
     * @return true se os métodos estiverem corretos, false caso contrário.
     */
    private boolean validateMethods(CriteriaByClass criteria, CorrectionResult result) {
        List<CriteriaMethod> expectedMethods = criteria.getMethods();
        boolean isValid = true;

        for (CriteriaMethod expectedMethod : expectedMethods) {
            Method matchingMethod = findMethod(expectedMethod);
            if (matchingMethod == null) {
                result.addMethodError(getName(), "Método esperado não encontrado: " + expectedMethod.getName());
                isValid = false;
            } else {
                result.addMethodSuccess(getName(), "Método encontrado: " + expectedMethod.getName());
                if (!matchingMethod.getReturnType().getSimpleName().equals(expectedMethod.getReturnType())) {
                    result.addMethodError(getName(), "Tipo de retorno incorreto no método: " + expectedMethod.getName());
                    isValid = false;
                } else {
                    result.addMethodSuccess(getName(), "Tipo de retorno validado no método: " + expectedMethod.getName());
                }
            }
        }

        return isValid;
    }

    private Method findMethod(CriteriaMethod expectedMethod) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(expectedMethod.getName())
                && method.getReturnType().getSimpleName().equals(expectedMethod.getReturnType())
                && matchParameters(method.getParameterTypes(), expectedMethod.getParameters())
                && matchVisibility(method.getModifiers(), expectedMethod.getVisibility())) {
                return method; // Método correspondente encontrado.
            }
        }
        return null; // Nenhum método correspondente encontrado.
    }

    /**
     * Verifica se os tipos de parâmetros correspondem ao esperado.
     *
     * @param parameterTypes     Tipos de parâmetros reais.
     * @param expectedParameters Parâmetros esperados do critério.
     * @return true se os parâmetros corresponderem, false caso contrário.
     */
    private boolean matchParameters(Class<?>[] parameterTypes, Map<String, String> expectedParameters) {
        if (parameterTypes.length != expectedParameters.size()) {
            return false; // Tamanho dos parâmetros não corresponde.
        }

        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].getSimpleName().equals(expectedParameters.values().toArray()[i])) {
                return false; // Tipo do parâmetro não corresponde.
            }
        }

        return true;
    }

    /**
     * Verifica se a visibilidade (modificadores) corresponde ao esperado.
     *
     * @param modifiers         Modificadores reais do membro (método/construtor).
     * @param expectedVisibility Visibilidade esperada (public, private, etc.).
     * @return true se a visibilidade corresponder, false caso contrário.
     */
    private boolean matchVisibility(int modifiers, String expectedVisibility) {
        return switch (expectedVisibility) {
            case "public" -> Modifier.isPublic(modifiers);
            case "private" -> Modifier.isPrivate(modifiers);
            case "protected" -> Modifier.isProtected(modifiers);
            default -> false; // Visibilidade desconhecida.
        };
    }
}
