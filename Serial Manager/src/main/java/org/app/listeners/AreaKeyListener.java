package org.app.listeners;

import org.app.components.MainPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AreaKeyListener implements KeyListener {

    private final MainPanel mainPanel;


    public AreaKeyListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER) {
            SwingUtilities.invokeLater(mainPanel::deletePlaceholderFromArea);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
