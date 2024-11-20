package com.reflectionsoftware.util.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.reflectionsoftware.model.clazz.ClassInfo;
import com.reflectionsoftware.model.result.compilation.CompilationResult;
import com.reflectionsoftware.service.compilation.CompilationService;
import com.reflectionsoftware.service.file.ClassFileLoader;
import com.reflectionsoftware.service.file.FileService;

public class ClassProcessor implements IClassProcessor {

    @Override
    public Map<String, List<ClassInfo>> processDirectoryToClasses(File directory) {
        Map<String, List<ClassInfo>> map = new LinkedHashMap<>();

        File[] subDirs = directory.listFiles(File::isDirectory);
        if (subDirs == null || subDirs.length == 0) {
            System.out.println("Nenhum passo encontrado no diretório.");
            return map;
        }

        for (File subDir : subDirs) {
            String subDirName = subDir.getName();
            List<ClassInfo> classInfos = processSubDir(subDir);
            map.put(subDirName, classInfos);
        }

        return map;
    }

    private List<ClassInfo> processSubDir(File subDirectory) {
        List<ClassInfo> classInfos = new ArrayList<>();
    
        List<File> javaFiles = FileService.getAllJavaFiles(subDirectory);
        if (javaFiles.isEmpty()) {
            System.out.println("Nenhum arquivo Java encontrado no passo: " + subDirectory.getName());
            return classInfos;
        }

        CompilationResult compilationResult = CompilationService.compileClasses(subDirectory, javaFiles);

        if(!compilationResult.isSuccess()){
            classInfos.add(new ClassInfo(compilationResult));
            return classInfos;
        }
        
        for (Class<?> clazz : ClassFileLoader.loadClasses(compilationResult.getCompilationDirectory(), compilationResult.getCompiledFiles())) {
            classInfos.add(new ClassInfo(clazz));
        }

        return classInfos;
    }    
}
