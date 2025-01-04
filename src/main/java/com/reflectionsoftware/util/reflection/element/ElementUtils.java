package com.reflectionsoftware.util.reflection.element;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;

import com.reflectionsoftware.util.reflection.annotation.AnnotationExtractor;

public class ElementUtils {

    public static boolean checkVisibility(Object template, Object student) {
        if (!canValidateVisibility(template, student)) return false;

        int templateModifiers = getModifiers(template);
        int studentModifiers = getModifiers(student);

        return (Modifier.isPublic(templateModifiers) == Modifier.isPublic(studentModifiers)) &&
               (Modifier.isProtected(templateModifiers) == Modifier.isProtected(studentModifiers)) &&
               (Modifier.isPrivate(templateModifiers) == Modifier.isPrivate(studentModifiers));
    }

    public static boolean checkModifiers(Object template, Object student) {
        if (!canValidateModifiers(template, student)) return false;

        int templateModifiers = getModifiers(template);
        int studentModifiers = getModifiers(student);

        boolean areStatic = Modifier.isStatic(templateModifiers) == Modifier.isStatic(studentModifiers);
        boolean areFinal = Modifier.isFinal(templateModifiers) == Modifier.isFinal(studentModifiers);

        return areStatic && areFinal;
    }

    public static boolean checkParameters(Object template, Object student) {
        if (!canValidateParameters(template, student)) return false;
    
        Class<?>[] templateParams = getParameterTypes(template);
        Class<?>[] studentParams = getParameterTypes(student);
    
        // Ordena os parâmetros de ambos os lados antes de compará-los
        Arrays.sort(templateParams, (a, b) -> a.getName().compareTo(b.getName()));
        Arrays.sort(studentParams, (a, b) -> a.getName().compareTo(b.getName()));
    
        return Arrays.equals(templateParams, studentParams);
    }
    

    public static boolean checkReturnType(Object template, Object student) {
        if (!canValidateReturnType(template, student)) return false;

        Method templateMethod = (Method) template;
        Method studentMethod = (Method) student;

        return templateMethod.getReturnType().getSimpleName().equals(studentMethod.getReturnType().getSimpleName());
    }

    public static boolean checkType(Object template, Object student) {
        if (!canValidateType(template, student)) return false;

        Field templateField = (Field) template;
        Field studentField = (Field) student;

        return templateField.getType().equals(studentField.getType());
    }

    public static boolean checkTest(Object template, Object student) {

        if(AnnotationExtractor.getTest(template) == null) return true;

        System.out.println("Métodos a serem comparados: " + template.toString() + " / " + student.toString());
    
        if (!canValidateTest(template, student) || !checkParameters(template, student) || !checkReturnType(template, student)) return false;
    
        System.out.println("Foram validados");
    
        Method templateMethod = (Method) template;
        Method studentMethod = (Method) student;
    
        String[] methodParameters = getMethodParametersTest(templateMethod);
        String[] constructorParameters = getConstructorParametersTest(templateMethod);
    
        System.out.println("Parametros método do template: " + Arrays.toString(methodParameters));
        System.out.println("Parametros construtor do template: " + Arrays.toString(constructorParameters));
    
        try {
            // Tipos esperados para os parâmetros do método
            Class<?>[] methodParameterTypes = templateMethod.getParameterTypes();
            System.out.println("Tipos esperados no método: " + Arrays.toString(methodParameterTypes));
    
            // Converta os parâmetros da anotacão para os tipos correspondentes no método
            Object[] convertedMethodParameters = convertParameters(templateMethod, methodParameters);
            System.out.println("Parâmetros do método convertidos: " + Arrays.toString(convertedMethodParameters));

            Constructor<?> matchingTemplateConstructor = findConstructorWithParameters(templateMethod.getDeclaringClass(), constructorParameters.length);
            Constructor<?> matchingStudentConstructor = findConstructorWithParameters(studentMethod.getDeclaringClass(), constructorParameters.length);
    
            Class<?>[] constructorParameterTypes = matchingTemplateConstructor.getParameterTypes();
            System.out.println("Tipos esperados no construtor: " + Arrays.toString(constructorParameterTypes));
    
            // Converta os parâmetros do construtor para os tipos correspondentes
            Object[] convertedConstructorParameters = convertConstructorParameters(constructorParameters, constructorParameterTypes);
            System.out.println("Parâmetros do construtor convertidos: " + Arrays.toString(convertedConstructorParameters));
    
            // Crie instâncias para invocar os métodos, se necessário
            Object templateInstance = Modifier.isStatic(templateMethod.getModifiers()) ? null : matchingTemplateConstructor.newInstance(convertedConstructorParameters);
            Object studentInstance = Modifier.isStatic(studentMethod.getModifiers()) ? null : matchingStudentConstructor.newInstance(convertedConstructorParameters);
    
            System.out.println("Instância do template: " + templateInstance);
            System.out.println("Instância do estudante: " + studentInstance);
    
            // Execute ambos os métodos com os mesmos parâmetros
            Object templateResult = templateMethod.invoke(templateInstance, convertedMethodParameters);
            Object studentResult = studentMethod.invoke(studentInstance, convertedMethodParameters);
    
            System.out.println("Objetos de saída: " + templateResult + " / " + studentResult);
    
            // Compare os resultados
            return compareResults(templateResult, studentResult);
    
        } catch (Exception e) {
            e.printStackTrace(); // Exibe o stack trace completo
            return false;
        }
    }

    private static Constructor<?> findConstructorWithParameters(Class<?> declaringClass, int parametersLength) {
        if(parametersLength == 0) return null;

        Constructor<?>[] constructors = declaringClass.getConstructors();
        System.out.println("Quantidade de construtores na classe: " + constructors.length);

        Constructor<?> matchingConstructor = null;
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == parametersLength) {
                    matchingConstructor = constructor;
                    System.out.println("Achou o construtor: " + constructor.getParameterCount());
                    return matchingConstructor;
                }
            }

        return null;
    }
    
    
    private static boolean compareResults(Object result1, Object result2) {
        // Lida com possíveis comparações entre primitivos e objetos
        if (result1 == null || result2 == null) {
            return result1 == result2;
        }
        return result1.equals(result2);
    }
    


    // --- ANOTAÇÕES ---

    public static boolean allFieldsPrivate(Object element) {
        try {
            Annotation annotation = AnnotationExtractor.getSpecification(element);
            if (annotation == null) return true;

            Method valueMethod = annotation.annotationType().getMethod("todosAtributosPrivados");
    
            return (boolean) valueMethod.invoke(annotation);
        } 
        catch (Exception e) { return true; }
    }

    public static boolean isAttributesExact(Object element) {
        try {
            Annotation annotation = AnnotationExtractor.getSpecification(element);
            if (annotation == null) return false;

            Method valueMethod = annotation.annotationType().getMethod("atributosExatos");
    
            return (boolean) valueMethod.invoke(annotation);
        } 
        catch (Exception e) { return false; }
    }

    public static boolean isConstructorsExact(Object element) {
        try {
            Annotation annotation = AnnotationExtractor.getSpecification(element);
            if (annotation == null) return false;

            Method valueMethod = annotation.annotationType().getMethod("construtoresExatos");
    
            return (boolean) valueMethod.invoke(annotation);
        } 
        catch (Exception e) { return false; }
    }

    public static boolean isMethodsExact(Object element) {
        try {
            Annotation annotation = AnnotationExtractor.getSpecification(element);
            if (annotation == null) return false;

            Method valueMethod = annotation.annotationType().getMethod("metodosExatos");
    
            return (boolean) valueMethod.invoke(annotation);
        } 
        catch (Exception e) { return false; }
    }

    public static double getGrade(Object element) {
        try {
            Annotation annotation = AnnotationExtractor.getGrade(element);
            if (annotation == null) return 0.0;

            Method valueMethod = annotation.annotationType().getMethod("value");
    
            return (double) valueMethod.invoke(annotation);
        } 
        catch (Exception e) { return 0.0; }
    }

    public static double getPenalty(Object element) {
        try {
            Annotation annotation = AnnotationExtractor.getSpecification(element);
            if (annotation == null) return 0.0;

            Method valueMethod = annotation.annotationType().getMethod("penalidade");
    
            return (double) valueMethod.invoke(annotation);
        } 
        catch (Exception e) { return 0.0; }
    }

    public static double getSimilarityThreshold(Object element) {
        try {
            Annotation annotation = AnnotationExtractor.getSpecification(element);
            if (annotation == null) return 0.0;

            Method valueMethod = annotation.annotationType().getMethod("similaridade");
    
            return (double) valueMethod.invoke(annotation);
        } 
        catch (Exception e) { return 0.0; }
    }

    private static String[] getMethodParametersTest(Method method) {
        try {
            Annotation annotation = AnnotationExtractor.getTest(method);
            if (annotation == null) {
                System.out.println("Não achou a anotacao");
                return new String[0];
            }
    
            Method valueMethod = annotation.annotationType().getMethod("parametros");
            System.out.println("Achou a anotacao: " + valueMethod.invoke(annotation));
            return (String[]) valueMethod.invoke(annotation);
    
        } catch (Exception e) {
            return new String[0];
        }
    }

    private static String[] getConstructorParametersTest(Method method) {
        try {
            Annotation annotation = AnnotationExtractor.getTest(method);
            if (annotation == null) {
                return new String[0];
            }
    
            Method valueMethod = annotation.annotationType().getMethod("construtor");
            return (String[]) valueMethod.invoke(annotation);
    
        } catch (Exception e) {
            return new String[0];
        }
    }
    

    // --- Métodos Auxiliares ---

    private static int getModifiers(Object element) {
        if (element instanceof Field) return ((Field) element).getModifiers();
        if (element instanceof Method) return ((Method) element).getModifiers();
        if (element instanceof Constructor) return ((Constructor<?>) element).getModifiers();
        return 0;
    }

    private static Class<?>[] getParameterTypes(Object element) {
        if (element instanceof Method) return ((Method) element).getParameterTypes();
        if (element instanceof Constructor) return ((Constructor<?>) element).getParameterTypes();
        return new Class<?>[0];
    }


    // --- VALIDADORES ---

    private static boolean canValidateVisibility(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return (element1 instanceof Constructor<?> && element2 instanceof Constructor<?>) ||
               (element1 instanceof Field && element2 instanceof Field) ||
               (element1 instanceof Method && element2 instanceof Method);
    }

    private static boolean canValidateModifiers(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return  (element1 instanceof Constructor<?> && element2 instanceof Constructor<?>) ||
                (element1 instanceof Field && element2 instanceof Field) ||
                (element1 instanceof Method && element2 instanceof Method);
    }

    private static boolean canValidateReturnType(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return (element1 instanceof Method && element2 instanceof Method);
    }  

    private static boolean canValidateParameters(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return (element1 instanceof Method && element2 instanceof Method) ||
               (element1 instanceof Constructor<?> && element2 instanceof Constructor<?>);
    }

    private static boolean canValidateType(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return (element1 instanceof Field && element2 instanceof Field);
    }

    private static boolean canValidateTest(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return (element1 instanceof Method && element2 instanceof Method);
    }

    private static Object[] convertParameters(Method method, String[] parameters) throws IllegalArgumentException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        
        if (parameters.length != parameterTypes.length) {
            throw new IllegalArgumentException("O número de parâmetros não corresponde ao esperado.");
        }
    
        Object[] convertedParameters = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            convertedParameters[i] = convertToType(parameters[i], parameterTypes[i]);
        }
    
        return convertedParameters;
    }

    private static Object[] convertConstructorParameters(String[] parameters, Class<?>[] parameterTypes) throws IllegalArgumentException {
        if (parameters.length != parameterTypes.length) {
            throw new IllegalArgumentException("O número de parâmetros do construtor não corresponde ao esperado.");
        }
    
        Object[] convertedParameters = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            convertedParameters[i] = convertToType(parameters[i], parameterTypes[i]);
        }
    
        return convertedParameters;
    }
    
    private static Object convertToType(String parameter, Class<?> type) throws IllegalArgumentException {
        try {
            if (type == int.class || type == Integer.class) {
                return Integer.parseInt(parameter);
            } else if (type == double.class || type == Double.class) {
                return Double.parseDouble(parameter);
            } else if (type == float.class || type == Float.class) {
                return Float.parseFloat(parameter);
            } else if (type == boolean.class || type == Boolean.class) {
                return Boolean.parseBoolean(parameter);
            } else if (type == long.class || type == Long.class) {
                return Long.parseLong(parameter);
            } else if (type == String.class) {
                return parameter;
            } else {
                throw new IllegalArgumentException("Tipo não suportado: " + type.getName());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Falha ao converter parâmetro '" + parameter + "' para o tipo " + type.getName());
        }
    }
    
    
}
