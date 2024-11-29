package org.app.listeners;

import org.app.components.MainPanel;
import org.app.util.backup.Backuper;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainFrameWindowListener implements WindowListener {

    private final MainPanel mainPanel;

    public MainFrameWindowListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        String backupSerials = Backuper.loadBackupFile();
        if (!backupSerials.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Salvataggio precedente trovato!", "Salvataggio recuperato", JOptionPane.INFORMATION_MESSAGE);
            mainPanel.getTextArea().setText(backupSerials);
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (!mainPanel.isEmptyArea()) {
            int risp = JOptionPane.showConfirmDialog(null, "Sono presenti dei seriali, vuoi salvarli?", "Attenzione!", JOptionPane.YES_NO_OPTION);
            if (risp == JOptionPane.OK_OPTION) {
                Backuper.backupSerials(mainPanel.getTextArea().getText().trim());
                JOptionPane.showMessageDialog(null, "Salvataggio effettuato!", "Seriali salvati", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
