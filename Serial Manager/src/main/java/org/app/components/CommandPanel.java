package org.app.components;

import org.app.util.DuplicateFinder;
import org.app.util.ExtractList;
import org.app.util.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

public class CommandPanel extends JPanel {

    private final int width = 500;
    private final int height = 100;

    private JButton checkDuplicateButton;
    private JButton generateTextFileButton;

    private final MainPanel mainPanel;

    public CommandPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        int x = (MainPanel.width - width) / 2;
        int y = MainPanel.height - height - 20;

        setBounds(x, y, width, height);
        setBackground(Color.white);
        setLayout(null);

        checkDuplicateButton = new JButton("Controlla duplicati");
        generateTextFileButton = new JButton("Genera file");

        int buttonWidth = 150;
        int buttonHeight = 30;

        int buttonX1 = (width / 2) - buttonWidth - 10;  // X per il primo bottone
        int buttonX2 = (width / 2) + 10;               // X per il secondo bottone
        int buttonY = (height - buttonHeight) / 2 + 15;     // Y per centrare verticalmente

        checkDuplicateButton.setBounds(buttonX1, buttonY, buttonWidth, buttonHeight);
        generateTextFileButton.setBounds(buttonX2, buttonY, buttonWidth, buttonHeight);

        checkDuplicateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = mainPanel.getTextArea().getText();
                if(text.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, "Non ci sono seriali", "Attenzione!", JOptionPane.WARNING_MESSAGE);
                }else{
                    Set<String> result = DuplicateFinder.findDuplicates(text);
                    if(!result.isEmpty())
                        JOptionPane.showMessageDialog(null, "Ci sono duplicati: " + result, "Seriali duplicati", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Perfetto, ci sono: " + DuplicateFinder.count + " seriali", "Ottimo!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        generateTextFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(mainPanel.getTextArea().getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Non ci sono seriali", "Attenzione!", JOptionPane.WARNING_MESSAGE);
                        return;
                    }else {
                        List<String> serials = ExtractList.extractSerialsToSave(mainPanel.getTextArea().getText());
                        FileManager.saveToFile(serials);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Attenzione!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println("ciao");
            }
        });

        add(checkDuplicateButton);
        add(generateTextFileButton);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public JButton getCheckDuplicateButton() {
        return checkDuplicateButton;
    }

    public void setCheckDuplicateButton(JButton checkDuplicateButton) {
        this.checkDuplicateButton = checkDuplicateButton;
    }

    public JButton getGenerateTextFileButton() {
        return generateTextFileButton;
    }

    public void setGenerateTextFileButton(JButton generateTextFileButton) {
        this.generateTextFileButton = generateTextFileButton;
    }
}
