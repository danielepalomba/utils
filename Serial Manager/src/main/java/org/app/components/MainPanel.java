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

    private CommandPanel commandPanel;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private Menu menu;

    public MainPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);

        //textArea
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(scrollPanelX, scrollPanelY, areaWidth, areaHeight);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setFont(boldFont);

        commandPanel = new CommandPanel(this);

        //Menu
        menu = new Menu(this);

        //JLabel
        add(menu);
        add(scrollPane);
        add(commandPanel);
        repaint();
        revalidate();
    }

    public Font getBoldFont() {
        return boldFont;
    }

    public void setBoldFont(Font boldFont) {
        this.boldFont = boldFont;
    }

    public CommandPanel getCommandPanel() {
        return commandPanel;
    }

    public void setCommandPanel(CommandPanel commandPanel) {
        this.commandPanel = commandPanel;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }
}
