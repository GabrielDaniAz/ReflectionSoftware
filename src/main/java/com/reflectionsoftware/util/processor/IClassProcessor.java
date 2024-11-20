package com.reflectionsoftware.util.processor;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.reflectionsoftware.model.clazz.ClassInfo;

public interface IClassProcessor {
    Map<String, List<ClassInfo>> processDirectoryToClasses(File templateDirectory);
}
