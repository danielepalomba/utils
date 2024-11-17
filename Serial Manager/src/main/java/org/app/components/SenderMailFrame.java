package org.app.components;

import javax.swing.*;
import java.awt.*;

public class SenderMailFrame extends JFrame {

    public static int width = 500;
    public static int height = 600;

    public SenderMailFrame(){
        this.setSize(new Dimension(width, height));
        this.setTitle("Invia seriali via mail");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
    }
}
