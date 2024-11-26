package com.reflectionsoftware.service.unpacker;
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class DirectoryUnpacker {

    public static void unpackAll(File directory) throws IOException {
        Files.walk(directory.toPath())
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        if (file.toString().endsWith(".zip")) {
                            unpackZip(file);
                        } else if (file.toString().endsWith(".gz")) {
                            unpackGzip(file);
                        }
                    } catch (IOException e) {
                        System.err.println("Erro ao descompactar arquivo: " + file + " - " + e.getMessage());
                    }
                });
    }

    private static void unpackZip(Path zipFile) throws IOException {
        Path targetDir = zipFile.getParent(); // Diretório onde o ZIP está localizado

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
        }
    }

    private static void unpackGzip(Path gzipFile) throws IOException {
        Path outputFile = gzipFile.getParent().resolve(gzipFile.getFileName().toString().replaceFirst("[.][^.]+$", ""));
        try (GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(gzipFile.toFile()));
             OutputStream os = Files.newOutputStream(outputFile)) {
            gzis.transferTo(os);
        }
    }
}
