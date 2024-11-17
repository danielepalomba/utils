package org.app.components;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    //screen settings
    public static final int width = 1024;
    public static final int height = 768;

    //scrollPane settings
    public static final int areaWidth = 600;
    public static final int areaHeight = 600;
    public static final int scrollPanelX = (width - areaWidth) / 2;
    public static final int scrollPanelY = (height - areaHeight) / 2;

    //font
    Font boldFont = new Font("SansSerif", Font.BOLD, 14);

    private final JTextArea textArea;

    public MainPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);

        //textArea
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(scrollPanelX, scrollPanelY, areaWidth, areaHeight);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setFont(boldFont);

        CommandPanel commandPanel = new CommandPanel(this);

        //Menu
        Menu menu = new Menu(this);

        //JLabel
        add(menu);
        add(scrollPane);
        add(commandPanel);
        repaint();
        revalidate();
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
