package org.app.components;

import org.app.exception.FieldIncomplete;
import org.app.mail.MailSender;
import org.app.mail.TextFileGenerator;

import javax.swing.*;
import java.awt.*;


public class EmailSenderFrame extends JFrame {
    public EmailSenderFrame(MainPanel mainPanel) {
        // Configurazione della JFrame
        setTitle("Invio Email");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Pannello principale
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campo per la mail del destinatario
        JLabel emailLabel = new JLabel("Destinatario*:");
        JTextField emailField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel.add(emailField, gbc);
        gbc.weightx = 0;

        // Campo per l'oggetto della mail
        JLabel subjectLabel = new JLabel("Oggetto*:");
        JTextField subjectField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(subjectLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(subjectField, gbc);

        // Testo della mail
        JLabel bodyLabel = new JLabel("Testo:");
        JTextArea bodyArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(bodyArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(bodyLabel, gbc);
        gbc.gridy = 3;
        panel.add(scrollPane, gbc);
        gbc.gridwidth = 1;

        // Campo per il nome del file da allegare
        JLabel fileLabel = new JLabel("Nome file da allegare*:");
        JTextField fileField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(fileLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(fileField, gbc);

        // Bottone per inviare la mail
        JButton sendButton = new JButton("Invia");

        sendButton.addActionListener(e -> {
            String receiver = emailField.getText().toLowerCase();
            String subject = subjectField.getText();
            String body = bodyArea.getText();
            String fileName = fileField.getText();

            // Stampa i dati in console per verifica
            System.out.println("Destinatario: " + receiver);
            System.out.println("Oggetto: " + subject);
            System.out.println("Testo:\n" + body);
            System.out.println("File allegato: " + fileName);

            try {
                areValidFields(receiver, subject, body);
                MailSender.sendSerialsViaMail(body, subject, receiver, fileName, TextFileGenerator.createTempFileWithContent(mainPanel.getTextArea().getText()));
                JOptionPane.showMessageDialog(EmailSenderFrame.this, "Email inviata con successo!", "Conferma", JOptionPane.INFORMATION_MESSAGE);
                EmailSenderFrame.this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Attenzione!",  JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(sendButton, gbc);

        JLabel warningLabel = new JLabel("<html><center>Il file da allegare viene generato automaticamente.<br><br></center></html>");
        warningLabel.setForeground(Color.RED);
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(warningLabel, BorderLayout.SOUTH);

        // Aggiunta del pannello principale alla JFrame
        add(panel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void areValidFields(String receiver, String subject, String fileName) throws Exception {
        if(receiver.isEmpty() || subject.isEmpty() || fileName.isEmpty()) {
            throw new FieldIncomplete();
        }
    }
}

