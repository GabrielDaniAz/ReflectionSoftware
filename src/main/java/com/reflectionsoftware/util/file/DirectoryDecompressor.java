package com.reflectionsoftware.util.file;

import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DirectoryDecompressor {

    /**
     * Descompacta todos os arquivos ZIP em um diretório e coloca os arquivos descompactados no mesmo diretório.
     * Após a descompactação, o arquivo ZIP original é excluído.
     *
     * @param sourceDir  Diretório fonte com os arquivos compactados.
     * @throws IOException Caso ocorra algum erro durante a descompactação.
     */
    public static void decompressDirectory(File sourceDir) throws IOException {
        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            throw new IllegalArgumentException("O diretório fonte não existe ou não é um diretório válido: " + sourceDir);
        }

        // Processa os arquivos dentro do diretório
        for (File file : Objects.requireNonNull(sourceDir.listFiles())) {
            if (file.isDirectory()) {
                // Processa subdiretórios recursivamente
                decompressDirectory(file);
            } else if (file.getName().endsWith(".zip")) {
                // Descompacta o arquivo ZIP
                decompressZipFile(file, sourceDir);
            }
        }
    }

    /**
     * Descompacta um arquivo ZIP em um diretório de saída (o mesmo diretório onde o arquivo ZIP está localizado).
     *
     * @param zipFile    Arquivo ZIP a ser descompactado.
     * @param outputDir  Diretório onde os arquivos serão descompactados (o mesmo diretório do ZIP).
     * @throws IOException Caso ocorra algum erro durante a descompactação.
     */
    private static void decompressZipFile(File zipFile, File outputDir) throws IOException {
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new IOException("Não foi possível criar o diretório de saída: " + outputDir);
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File outputFile = new File(outputDir, entry.getName());
                if (entry.isDirectory()) {
                    // Cria diretórios para entradas de diretório
                    if (!outputFile.exists() && !outputFile.mkdirs()) {
                        throw new IOException("Não foi possível criar o diretório: " + outputFile);
                    }
                } else {
                    // Cria os arquivos extraídos
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }

        // Após a descompactação, exclui o arquivo ZIP original
        if (!zipFile.delete()) {
            throw new IOException("Não foi possível excluir o arquivo ZIP após a descompactação: " + zipFile);
        }
    }
}
