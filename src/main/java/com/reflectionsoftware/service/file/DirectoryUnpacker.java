package com.reflectionsoftware.service.file;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class DirectoryUnpacker {

    public static void unpackAll(File directory) {
        try {
            Files.walk(directory.toPath())
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        if (file.toString().endsWith(".zip")) {
                            unpackZip(file);
                        } else if (file.toString().endsWith(".gz")) {
                            unpackGzip(file);
                        }
                    });
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao percorrer os arquivos no diretório: " + directory.getPath() + " - " + e.getMessage());
        }
    }

    private static void unpackZip(Path zipFile) {
        Path targetDir = zipFile.getParent();

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile.toFile()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path extractedFile = targetDir.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(extractedFile);
                } else {
                    Files.createDirectories(extractedFile.getParent());
                    try (OutputStream os = Files.newOutputStream(extractedFile)) {
                        zis.transferTo(os);
                    }
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao descompactar o arquivo ZIP: " + zipFile + " - " + e.getMessage());
        }
    }

    private static void unpackGzip(Path gzipFile) {
        Path outputFile = gzipFile.getParent().resolve(gzipFile.getFileName().toString().replaceFirst("[.][^.]+$", ""));
        try (GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(gzipFile.toFile()));
             OutputStream os = Files.newOutputStream(outputFile)) {
            gzis.transferTo(os);
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao descompactar o arquivo GZIP: " + gzipFile + " - " + e.getMessage());
        }
    }
}
