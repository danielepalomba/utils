package org.app;

import org.app.components.MainPanel;
import org.app.listeners.MainFrameWindowListener;
import org.app.util.filemanager.EnvFileManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        EnvFileManager.initializeEnvFile();

        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setTitle("Gestore seriali");
            window.setResizable(false);
            MainPanel mainPanel = new MainPanel();
            window.add(mainPanel);
            window.pack();
            window.setLocationRelativeTo(null);
            window.addWindowListener(new MainFrameWindowListener(mainPanel));
            window.setVisible(true);
        });
    }
}