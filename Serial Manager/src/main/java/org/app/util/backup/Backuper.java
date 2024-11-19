package org.app.util.backup;

import javax.swing.*;
import java.io.*;

public class Backuper {

    private static final String BKP_DIRECTORY;
    private static final String BKP_FILE_PATH;

    static {
        // Determina la directory di configurazione in base al sistema operativo
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            BKP_DIRECTORY = System.getenv("APPDATA") + File.separator + "SerialManager" + File.separator + "Backup";
        } else {
            BKP_DIRECTORY = System.getProperty("user.home") + File.separator + ".config" + File.separator + "SerialManager" + File.separator + "Backup";
        }
        BKP_FILE_PATH = BKP_DIRECTORY + File.separator + "backup.txt";
    }

    public static void backupSerials(String serials) {
        File backupDirectory = new File(BKP_DIRECTORY);
        File backupFile = new File(BKP_FILE_PATH);

        if (!backupDirectory.exists()) {
            if (backupDirectory.mkdirs())
                System.out.println("Cartella di backup creata:" + backupDirectory.getAbsolutePath());
        }

        if (!backupFile.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(backupFile))) {
                bw.write(serials);
                System.out.println("Seriali correttamente salvati: " + backupFile.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Attenzione!", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static String loadBackupFile(){
        File backupDirectory = new File(BKP_DIRECTORY);
        File backupFile = new File(BKP_FILE_PATH);

        if(!backupDirectory.exists() || !backupFile.exists()){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(BKP_FILE_PATH))){
            String line;
            while((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Attenzione!", JOptionPane.ERROR_MESSAGE);
        }

        deleteFile(new File(BKP_DIRECTORY));

        return sb.toString();
    }

    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteFile(f);
                }
            }
        }
        boolean check = file.delete();
        if (!check) {
            JOptionPane.showMessageDialog(null, "Non è stato possibile eliminare il file di backup dei seriali!", "Attenzione!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
