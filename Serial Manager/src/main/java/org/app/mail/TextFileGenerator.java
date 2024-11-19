package org.app.mail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileGenerator {

    public static File createTempFileWithContent(String content) throws IOException {
        // Determina la directory temporanea in base al sistema operativo
        String tempDir = System.getProperty("java.io.tmpdir");

        String fileName = "tempfile_.txt";
        File tempFile = new File(tempDir, fileName);

        if (tempFile.exists()) {
            System.out.println("Il file temporaneo esiste già. Sovrascrittura in corso...");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(content);
        }

        // Segna il file per eliminazione automatica alla chiusura del programma
        tempFile.deleteOnExit();

        return tempFile;
    }
}
