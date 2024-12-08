package org.app.components;

import org.app.listeners.AreaKeyListener;
import org.app.listeners.AreaMouseListener;
import org.app.util.ExtractList;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class MainPanel extends JPanel {

    //screen settings
    public static final int width = 1024;
    public static final int height = 768;

    //scrollPane settings
    public static final int areaWidth = 600;
    public static final int areaHeight = 600;
    public static final int scrollPanelX = (width - areaWidth) / 2;
    public static final int scrollPanelY = (height - areaHeight) / 2;

    //Placeholder
    public static final String placeholder= "# Questo è un commento d'esempio\n\n# Premere INVIO per iniziare...";

    //font
    Font italicFont = new Font("SansSerif", Font.ITALIC, 16);
    Font boldFont = new Font("SansSerif", Font.BOLD, 14);

    private final JTextArea textArea;
    private final Menu menu;

    public MainPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);

        // textArea
        textArea = new JTextArea();
        textArea.setFont(italicFont);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(scrollPanelX, scrollPanelY, areaWidth, areaHeight);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        textArea.setForeground(Color.GRAY);
        textArea.setText(placeholder);

        textArea.addMouseListener(new AreaMouseListener(this));
        textArea.addKeyListener(new AreaKeyListener(this));

        CommandPanel commandPanel = new CommandPanel(this);

        // Alert
        JLabel alert = new JLabel("Digitare '#' per inserire un commento nella finestra di testo!");
        alert.setFont(boldFont);
        alert.setBounds(scrollPanelX, scrollPanelY - 40, areaWidth, 50);

        // Menu
        menu = new Menu(this);

        add(menu);
        add(scrollPane);
        add(commandPanel);
        add(alert);
        repaint();
        revalidate();
    }

    public JTextArea getTextArea() {
        return textArea;
    }
    public Menu getMenuBar() {return menu;}

    public boolean isEmptyArea() {
        if (textArea.getText().trim().isEmpty())
            return true;
        List<String> lines = ExtractList.extractTextFromArea(textArea.getText().trim());
        for (String line : Objects.requireNonNull(lines)) {
            if (!line.trim().startsWith("#"))
                return false;
        }
        return true;
    }

    public void deletePlaceholderFromArea() {
        if (textArea.getText().trim().startsWith(placeholder)) {
            textArea.setText("");
            textArea.setCaretPosition(0);
            textArea.setSelectionStart(0);
            textArea.setForeground(Color.BLACK);
            textArea.setEditable(true);
            textArea.revalidate();
        }
    }
}
