package org.app;

import org.app.components.MainPanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setTitle("Gestore seriali");
            window.setResizable(false);
            window.setLocationRelativeTo(null);

            MainPanel mainPanel = new MainPanel();
            window.add(mainPanel);
            window.pack();
            window.setVisible(true);
        });
    }
}