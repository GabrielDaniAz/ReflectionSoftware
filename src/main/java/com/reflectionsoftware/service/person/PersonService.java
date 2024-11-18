package com.reflectionsoftware.service.person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.reflectionsoftware.model.Template;
import com.reflectionsoftware.model.Professor;
import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.criteria.Criteria;
import com.reflectionsoftware.model.result.compilation.CompilationResult;
import com.reflectionsoftware.service.compilation.CompilationService;
import com.reflectionsoftware.service.criteria.CriteriaService;
import com.reflectionsoftware.service.file.ClassFileLoader;
import com.reflectionsoftware.service.file.FileService;
import com.reflectionsoftware.service.file.ReflectionClassConvert;

public class PersonService {

    private static final String BASE_DIRECTORY_CRITERIA = System.getProperty("user.dir") + File.separator + "template";

    /* --- MÉTODOS PÚBLICOS PARA OBTENÇÃO DE DADOS --- */

    public static List<Student> getStudents(String rootDirectory) throws Exception {
        Map<File, List<File>> javaFilesMap = FileService.getJavaFilesFromSubdirectories(rootDirectory);
        return compileAndCreateStudents(javaFilesMap);
    }

    public static Professor getProfessor(String jsonFilePath, int untilStep) throws Exception {
        Criteria criteria = loadCriteria(jsonFilePath, untilStep);
        ReflectionClassConvert.convertCriteriaToJava(criteria, BASE_DIRECTORY_CRITERIA);
        Map<File, List<File>> javaFilesMap = FileService.getJavaFilesFromSubdirectories(BASE_DIRECTORY_CRITERIA);

        List<CompilationResult> compilationResults = compileJavaFiles(javaFilesMap);

        List<Template> exercises = new ArrayList<>();
        for (CompilationResult compilationResult : compilationResults) {
            if(!compilationResult.isSuccess()){
                continue;
            }
            List<Class<?>> classes = ClassFileLoader.loadClasses(compilationResult.getCompilationDirectory(), compilationResult.getCompiledFiles());
            exercises.add(new Template(compilationResult.getRootDirectory().getName(), classes));
        }
        return new Professor(compilationResults, exercises, criteria);
    }

    /* --- MÉTODOS PRIVADOS AUXILIARES --- */

    private static List<Student> compileAndCreateStudents(Map<File, List<File>> javaFilesMap) throws Exception {
        List<Student> students = new ArrayList<>();
        for (Map.Entry<File, List<File>> entry : javaFilesMap.entrySet()) {
            try {
                Student student = createStudent(entry);
                students.add(student);
            } catch (Exception e) {
                System.err.println("Erro ao processar estudante no diretório " + entry.getKey().getName() + ": " + e.getMessage());
            }
        }
        return students;
    }

    private static Student createStudent(Map.Entry<File, List<File>> entry) throws Exception {
        CompilationResult compilationResult = compile(entry);
        List<Class<?>> classes = ClassFileLoader.loadClasses(compilationResult.getCompilationDirectory(), compilationResult.getCompiledFiles());
        return new Student(entry.getKey().getName(), compilationResult, classes);
    }

    private static CompilationResult compile(Map.Entry<File, List<File>> entry) throws Exception {
        return CompilationService.compileAndLoadClasses(entry.getKey(), entry.getValue());
    }

    private static Criteria loadCriteria(String jsonFilePath, int untilStep) throws IOException {
        return CriteriaService.getCriteria(jsonFilePath, untilStep);
    }

    private static List<CompilationResult> compileJavaFiles(Map<File, List<File>> javaFilesMap) throws Exception {
        List<CompilationResult> compilationResults = new ArrayList<>();
        for (Map.Entry<File, List<File>> entry : javaFilesMap.entrySet()) {
            try {
                CompilationResult result = compile(entry);
                compilationResults.add(result);
            } catch (Exception e) {
                System.err.println("Erro ao compilar arquivos no diretório " + entry.getKey().getName() + ": " + e.getMessage());
            }
        }
        return compilationResults;
    }
}

