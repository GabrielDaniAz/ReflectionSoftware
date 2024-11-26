package com.reflectionsoftware.util.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.result.compilation.CompilationResult;
import com.reflectionsoftware.service.compilation.CompilationService;
import com.reflectionsoftware.service.file.ClassFileLoader;
import com.reflectionsoftware.service.file.FileService;

public class StudentProcessor {

    public static List<Student> processStudentDirectory(File studentRootDirectory) {
        validateStudentRootDirectory(studentRootDirectory);

        List<Student> students = new ArrayList<>();
        File[] subDirs = studentRootDirectory.listFiles(File::isDirectory);

        for (File subDir : subDirs) {
            Student student = processSingleStudentDirectory(subDir);
            if (student != null) {
                students.add(student);
            }
        }

        return students;
    }

    private static void validateStudentRootDirectory(File studentRootDirectory) {
        File[] subDirs = studentRootDirectory.listFiles(File::isDirectory);
        if (subDirs == null || subDirs.length == 0) {
            throw new IllegalArgumentException("Nenhum subdiretório de aluno encontrado em: " + studentRootDirectory.getPath());
        }
    }

    private static Student processSingleStudentDirectory(File studentDirectory) {
        String studentName = getStudentName(studentDirectory.getName());
        List<File> javaFiles = FileService.getAllJavaFiles(studentDirectory);
        List<File> jarFiles = FileService.getAllJarFiles(studentDirectory);

        if (javaFiles.isEmpty()) {
            System.err.println("Nenhum arquivo Java encontrado no diretório: " + studentName);
            return new Student(studentName, Collections.emptyList(), null);
        }

        CompilationResult compilationResult = CompilationService.compileClasses(studentDirectory, javaFiles, jarFiles);

        if (!compilationResult.isSuccess()) {
            return new Student(studentName, Collections.emptyList(), compilationResult);
        }

        List<Class<?>> loadedClasses = loadCompiledClasses(compilationResult, studentName);
        return new Student(studentName, loadedClasses, compilationResult);
    }

    private static List<Class<?>> loadCompiledClasses(CompilationResult compilationResult, String studentName) {
        try {
            return ClassFileLoader.loadClasses(compilationResult.getCompilationDirectory(), compilationResult.getCompiledFiles(), compilationResult.getJarFiles());
        } catch (Exception e) {
            System.err.println("Erro ao carregar classes do aluno: " + studentName);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static String getStudentName(String studentName){
        if (studentName.contains(" - ")) {
            studentName = studentName.split(" - ")[0].trim();
        }
        return studentName;
    }
}
