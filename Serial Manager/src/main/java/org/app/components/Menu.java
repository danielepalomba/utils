package org.app.components;

import org.app.exception.NotEmptyArea;
import org.app.util.FileManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Menu extends JMenuBar {

    private final MainPanel mainPanel;
    private JMenuItem exit;
    private JMenuItem importFile;

    public Menu(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        setExitFunct();
        setImportFileFunct();

        setBounds(0, 0, MainPanel.width, MainPanel.height / 30);

    }

    private void setExitFunct() {
        exit = new JMenuItem("Exit");
        setExitActionListener();
        this.add(exit);
    }

    private void setExitActionListener() {
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    terminateProg();
                } catch (Exception ex) {
                    int i = JOptionPane.showConfirmDialog(
                            null,
                            ex.getMessage(),
                            "I dati andranno persi",
                            JOptionPane.OK_CANCEL_OPTION
                    );

                    if (i == JOptionPane.OK_OPTION) {
                        System.exit(0);
                    }
                }
            }
        });
    }

    private void terminateProg() throws Exception {
        if (mainPanel.getTextArea().getText().isEmpty()) {
            System.exit(0);
        } else {
            throw new NotEmptyArea();
        }
    }

    private void setImportFileFunct(){
        importFile = new JMenuItem("Import");
        setImportFileAction();
        this.add(importFile);
    }

    private void setImportFileAction(){
        importFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> serials = FileManager.importFromFile();
                mainPanel.getTextArea().setText("");
                mainPanel.getTextArea().repaint();
                mainPanel.getTextArea().setText(String.join("\n", serials));
            }
        });
    }
}
