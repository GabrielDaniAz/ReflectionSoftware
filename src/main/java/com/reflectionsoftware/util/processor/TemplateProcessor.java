package com.reflectionsoftware.util.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.reflectionsoftware.model.result.compilation.CompilationResult;
import com.reflectionsoftware.model.template.Template;
import com.reflectionsoftware.model.template.exercise.Exercise;
import com.reflectionsoftware.service.compilation.CompilationService;
import com.reflectionsoftware.service.file.ClassFileLoader;
import com.reflectionsoftware.service.file.FileService;

public class TemplateProcessor {

    public static Template processTemplateDirectory(File templateRootDirectory) {
        validateTemplateRootDirectory(templateRootDirectory);

        String templateName = templateRootDirectory.getName();
        List<Exercise> exercises = new ArrayList<>();

        File[] subDirs = templateRootDirectory.listFiles(File::isDirectory);
        for (File subDir : subDirs) {
            Exercise exercise = processSingleExerciseDirectory(subDir);
            exercises.add(exercise); // Adicionado diretamente, pois a validação não permite nulo.
        }

        return new Template(templateName, exercises);
    }

    private static void validateTemplateRootDirectory(File templateRootDirectory) {
        File[] subDirs = templateRootDirectory.listFiles(File::isDirectory);
        if (subDirs == null || subDirs.length == 0) {
            throw new IllegalArgumentException("Nenhum subdiretório de exercícios encontrado em: " + templateRootDirectory.getPath());
        }
    }

    private static Exercise processSingleExerciseDirectory(File exerciseDirectory) {
        if (exerciseDirectory == null || !exerciseDirectory.isDirectory()) {
            throw new IllegalArgumentException("Diretório de exercício inválido: " + exerciseDirectory);
        }

        String exerciseName = exerciseDirectory.getName();
        List<File> javaFiles = FileService.getAllJavaFiles(exerciseDirectory);
        List<File> jarFiles = FileService.getAllJarFiles(exerciseDirectory);

        if (javaFiles.isEmpty()) {
            throw new IllegalStateException("Nenhum arquivo Java encontrado no exercício: " + exerciseName);
        }

        CompilationResult compilationResult = CompilationService.compileClasses(exerciseDirectory, javaFiles, jarFiles);

        if (!compilationResult.isSuccess()) {
            // Construir a mensagem detalhada com base nos diagnósticos
            StringBuilder errorMessage = new StringBuilder("Erro de compilação no exercício: " + exerciseName + "\n");

            compilationResult.getDiagnostics().forEach(diagnostic -> {
                errorMessage.append("Código: ").append(diagnostic.getCode()).append("\n")
                            .append("Mensagem: ").append(diagnostic.getMessage(Locale.getDefault())).append("\n")
                            .append("Localização: ").append(diagnostic.getSource()).append(" linha: ")
                            .append(diagnostic.getLineNumber()).append("\n")
                            .append("----------------------------------------------------\n");
            });

            // Lançar a exceção com a mensagem detalhada
            throw new IllegalStateException(errorMessage.toString());
        }


        List<Class<?>> loadedClasses = loadCompiledClasses(compilationResult, exerciseName);
        return new Exercise(exerciseName, loadedClasses);
    }

    private static List<Class<?>> loadCompiledClasses(CompilationResult compilationResult, String exerciseName) {
        try {
            return ClassFileLoader.loadClasses(compilationResult.getCompilationDirectory(), compilationResult.getCompiledFiles(), compilationResult.getJarFiles());
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar classes compiladas para o exercício: " + exerciseName, e);
        }
    }
}
