package model;

import java.util.Arrays;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionResult {
    
    private Class<?> clazz;
    private Constructor<?>[] constructors;
    private Field[] fields;
    private Method[] methods;

    public ReflectionResult(Class<?> clazz){
        this.clazz = clazz;
        this.constructors = clazz.getConstructors();
        this.fields = clazz.getDeclaredFields();
        this.methods = clazz.getDeclaredMethods();
    }

    public String generateDetails() {
        String details = String.format("""
        %s

        %s

        %s

        %s
        """, classInformation(), constructorsInformation(), fieldsInformation(), methodsInformation());

        return details;
    }

    private String classInformation() {
        String superclassesString = clazz.getSuperclass() != null ? clazz.getSuperclass().getName() : "None";
        String interfaceString = (clazz.getInterfaces().length > 0) ? Arrays.toString(clazz.getInterfaces()) : "None";
        String declaredClassesString = (clazz.getDeclaredClasses().length > 0) ? Arrays.toString(clazz.getDeclaredClasses()) : "None";

        return String.format("""
            - Classe %s:
                > SuperClass: %s,
                > Interface: %s,
                > Classes Declaradas: %s,
                > Quantidade de Construtores: %d,
                > Quantidade de Atributos: %d,        
                > Quantidade de Métodos: %d,
        """,
        clazz.getName(), superclassesString, interfaceString, declaredClassesString, constructors.length, fields.length, methods.length);
    }

    private String constructorsInformation() {
        String information = String.format("""
            - Construtores: 
                > Quantidade de Construtores: %d,       
        """, constructors.length);

        int position = 1;
        for (Constructor<?> constructor : constructors) {
            String parameters = (constructor.getParameterTypes().length > 0) ? Arrays.toString(constructor.getParameterTypes()) : "None";
            
            information += String.format("""
                        - Construtor (%d):
                            # Parâmmetros: %s
            """, 
            position, parameters);

            position++;
        }
        return information;
    }

    private String methodsInformation(){
        String information = String.format("""
            - Métodos: 
                > Quantidade de Métodos: %d,       
        """, methods.length);

        for (Method method : methods) {
            String parameters = (method.getParameterTypes().length > 0) ? Arrays.toString(method.getParameterTypes()) : "None";

            information += String.format("""
                        - Método: %s,
                            # Modificador: %d,
                            # Quantidade de parâmetros: %d,
                            # Parâmetros: %s,
                            # Return: %s
            """, 
            method.getName(), method.getModifiers(), method.getParameterCount(), 
            parameters, method.getReturnType().getSimpleName()
            );
        }
        return information;
    }

    private String fieldsInformation(){
        String information = String.format("""
            - Atributos: 
                > Quantidade de Atributos: %d,       
        """, fields.length);

        for (Field field : fields) {
            information += String.format("""
                        - Atributo: %s,
                            # Modificador: %d,
                            # Tipo: %s
            """,
            field.getName(), field.getModifiers(), field.getType());
        }

        return information;
    }
}
