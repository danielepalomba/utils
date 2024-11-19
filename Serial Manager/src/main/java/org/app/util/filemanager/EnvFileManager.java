package org.app.util.filemanager;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class EnvFileManager {

    private static final String ENV_DIRECTORY;
    private static final String ENV_FILE_PATH;

    static {
        // Determina la directory di configurazione in base al sistema operativo
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            ENV_DIRECTORY = System.getenv("APPDATA") + File.separator + "SerialManager";
        } else {
            ENV_DIRECTORY = System.getProperty("user.home") + File.separator + ".config" + File.separator + "SerialManager";
        }
        ENV_FILE_PATH = ENV_DIRECTORY + File.separator + ".env";
    }


    public static Map<String, String> readEnvFile() {
        Map<String, String> envVars = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ENV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    envVars.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return envVars;
    }

    public static void updateEnvFile(String email, String password) {

        Map<String, String> envVars = readEnvFile();

        envVars.put("EMAIL", email);
        envVars.put("PASSWORD", password);

        // Scrive i nuovi valori nel file .env
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ENV_FILE_PATH))) {
            for (Map.Entry<String, String> entry : envVars.entrySet()) {
                bw.write(entry.getKey() + "=" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        System.out.println("File .env aggiornato!");
    }

    public static void initializeEnvFile() {
        File envDirectory = new File(ENV_DIRECTORY);
        File envFile = new File(ENV_FILE_PATH);

        try {
            // Crea la directory se non esiste
            if (!envDirectory.exists()) {
                if (envDirectory.mkdirs()) {
                    System.out.println("Directory di configurazione creata: " + ENV_DIRECTORY);
                }
            }

            // Crea il file .env se non esiste
            if (!envFile.exists()) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(envFile))) {
                    bw.write("EMAIL=");
                    bw.newLine();
                    bw.write("PASSWORD=");
                    bw.newLine();
                }
                System.out.println("File .env inizializzato: " + ENV_FILE_PATH);
            } else {
                System.out.println("File .env già esistente: " + ENV_FILE_PATH);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String getFormattedCredentials(){
        Map<String, String> envVars = readEnvFile();
        String email = envVars.get("EMAIL");
        String password = envVars.get("PASSWORD");

        return "Email: " + email + "\n" + "Password: " + password + "\n";
    }
}
