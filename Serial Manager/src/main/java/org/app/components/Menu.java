package org.app.components;

import org.app.exception.NotEmptyArea;
import org.app.util.backup.Backuper;
import org.app.util.filemanager.EnvFileManager;
import org.app.util.ExtractList;
import org.app.util.filemanager.ImportExportFileManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Menu extends JMenuBar {

    private final MainPanel mainPanel;

    private JMenuItem exit;
    private JMenuItem importFile;
    private JMenuItem exportFile;
    private JMenuItem sendNewMail;
    private JMenuItem credentials;
    private JMenuItem showCredentials;
    private JMenu savedUserMenu;

    //variabile di controllo
    private EmailSenderFrame senderMailFrame;

    public Menu(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        setExitFunct();
        setImportFileFunct();
        setSendViaMailFunct();
        setExportFileFunct();
        setCredentialsFunct();
        setShowCredentialsFunct();

        setItemInMenu();

        setBounds(0, 0, MainPanel.width, MainPanel.height / 30);

    }

    private void setExitFunct() {
        exit = new JMenuItem("Exit");
        setExitActionListener();
    }

    private void setExitActionListener() {
        exit.addActionListener(e -> {
            try {
                if (mainPanel.getTextArea().getText().isEmpty())
                    terminateProg();
                else {
                    int risp = JOptionPane.showConfirmDialog(null, "Sono presenti dei seriali, vuoi salvarli?", "Attenzione!", JOptionPane.YES_NO_OPTION);
                    if (risp == JOptionPane.OK_OPTION) {
                        Backuper.backupSerials(mainPanel.getTextArea().getText().trim());
                        JOptionPane.showMessageDialog(null, "Salvataggio effettuato!", "Seriali salvati", JOptionPane.INFORMATION_MESSAGE);
                    } else if (risp == JOptionPane.NO_OPTION) {
                        terminateProg();
                    }
                }
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
        });
    }

    private void terminateProg() throws Exception {
        if (mainPanel.getTextArea().getText().isEmpty()) {
            System.exit(0);
        } else {
            throw new NotEmptyArea();
        }
    }

    private void setImportFileFunct() {
        importFile = new JMenuItem("Importa");
        setImportFileAction();
    }

    private void setImportFileAction() {
        importFile.addActionListener(e -> {
            List<String> serials = ImportExportFileManager.importFromFile();
            mainPanel.getTextArea().setText("");
            mainPanel.getTextArea().repaint();
            mainPanel.getTextArea().setText(String.join("\n", serials));
        });
    }

    private void setExportFileFunct() {
        exportFile = new JMenuItem("Esporta");
        setExportFileAction();
    }

    private void setExportFileAction() {
        exportFile.addActionListener(e -> {
            try {
                if (mainPanel.getTextArea().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Non ci sono seriali", "Attenzione!", JOptionPane.WARNING_MESSAGE);
                } else {
                    List<String> serials = ExtractList.extractSerialsToSave(mainPanel.getTextArea().getText());
                    ImportExportFileManager.saveToFile(serials);
                    mainPanel.getTextArea().setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Attenzione!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void setSendViaMailFunct() {
        sendNewMail = new JMenuItem("Nuova mail");
        setSendViaMailAction();
    }

    private void setSendViaMailAction() {
        sendNewMail.addActionListener(e -> {
            if (senderMailFrame == null || !senderMailFrame.isDisplayable()) {
                SwingUtilities.invokeLater(() -> senderMailFrame = new EmailSenderFrame(mainPanel));
            } else {
                senderMailFrame.toFront();
                senderMailFrame.requestFocus();
            }
        });
    }

    private void setCredentialsFunct() {
        credentials = new JMenuItem("Imposta Credenziali");
        setCredentialsAction();
    }

    private void setCredentialsAction() {
        credentials.addActionListener(e -> {
            String email = "";
            String password = "";

            while (email.isEmpty()) {
                email = JOptionPane.showInputDialog("Email:");
                if (email != null && email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Il campo 'Email' è necessario!", "Attenzione!", JOptionPane.ERROR_MESSAGE);
                }
            }

            while (password.isEmpty()) {
                password = JOptionPane.showInputDialog("Password:");
                if (password != null && password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Il campo 'Password' è necessario!", "Attenzione!", JOptionPane.ERROR_MESSAGE);
                }
            }

            EnvFileManager.updateCredentialsEnvFile(email, password);
        });
    }

    private void setShowCredentialsFunct() {
        showCredentials = new JMenuItem("Mostra Credenziali");
        setShowCredentialsAction();
    }

    private void setShowCredentialsAction() {
        showCredentials.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, EnvFileManager.getFormattedCredentials(), "Le tue credenziali", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void setItemInMenu() {
        JMenu emailMenu = new JMenu("Email");
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        savedUserMenu = new JMenu("Invia a");

        helpMenu.add(exit);
        emailMenu.add(sendNewMail);
        emailMenu.add(credentials);
        emailMenu.add(showCredentials);

        List<String> emails = EnvFileManager.readSavedEmails();
        loadSavedEmailActionListener(emails);

        fileMenu.add(importFile);
        fileMenu.add(exportFile);

        emailMenu.add(savedUserMenu);
        add(fileMenu);
        add(emailMenu);
        add(helpMenu);
    }

    public void reloadSavedEmails() {
        List<String> emails = EnvFileManager.readSavedEmails();
        savedUserMenu.removeAll();
        loadSavedEmailActionListener(emails);
        savedUserMenu.repaint();
        savedUserMenu.revalidate();
    }

    private void loadSavedEmailActionListener(List<String> emails) {
        for (String email : emails) {
            if (!email.isEmpty()) {
                JMenuItem menuItem = new JMenuItem(email);
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SwingUtilities.invokeLater(() -> {
                            new EmailSenderFrame(mainPanel, email);
                        });
                    }
                });
                savedUserMenu.add(menuItem);
            }
        }
    }
}
