package org.app.components;

import org.app.util.DuplicateFinder;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class CommandPanel extends JPanel {

    private final int width = 500;
    private final int height = 100;

    public CommandPanel(MainPanel mainPanel) {

        int x = (MainPanel.width - width) / 2;
        int y = MainPanel.height - height - 20;

        setBounds(x, y, width, height);
        setBackground(Color.white);
        setLayout(null);

        JButton checkDuplicateButton = new JButton("Controlla duplicati");

        int buttonWidth = 150;
        int buttonHeight = 30;

        int buttonX1 = (width / 2) - (buttonWidth / 2);
        int buttonY = (height - buttonHeight) / 2 + 15;     // Y per centrare verticalmente

        checkDuplicateButton.setBounds(buttonX1, buttonY, buttonWidth, buttonHeight);

        checkDuplicateButton.addActionListener(e -> {
            String text = mainPanel.getTextArea().getText().trim();
            if(text.isEmpty()){
                JOptionPane.showMessageDialog(mainPanel, "Non ci sono seriali", "Attenzione!", JOptionPane.WARNING_MESSAGE);
            }else{
                Set<String> result = DuplicateFinder.findDuplicates(text);
                if(!result.isEmpty())
                    JOptionPane.showMessageDialog(null, "Ci sono duplicati: " + result, "Seriali duplicati", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "Perfetto, ci sono: " + DuplicateFinder.count + " seriali", "Ottimo!", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(checkDuplicateButton);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}

