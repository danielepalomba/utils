package org.app.util.filemanager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImportExportFileManager {

    public static void saveToFile(List<String> lines) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Scegli dove salvare il file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getName().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(null, "File salvato con successo!", "Perfetto", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Errore nel salvataggio del file: " + e.getMessage());
            }
        }
    }

    public static List<String> importFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Scegli un file di testo");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("File di testo (*.txt)", "txt");
        fileChooser.setFileFilter(txtFilter);

        int userSelection = fileChooser.showOpenDialog(null);
        List<String> lines = new ArrayList<>();

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                JOptionPane.showMessageDialog(null, "File importato con successo!", "Perfetto", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Errore nell'importazione del file: " + e.getMessage());
            }
        }
        return lines;
    }
}
