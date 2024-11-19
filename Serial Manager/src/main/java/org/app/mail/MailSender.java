package org.app.mail;

import jakarta.activation.FileDataSource;
import org.app.exception.CredentialsNotFound;
import org.app.exception.InvalidEmailFormat;
import org.app.util.filemanager.EnvFileManager;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.api.mailer.Mailer;


import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailSender {

    public static void sendSerialsViaMail(String body, String subject, String receiver, String fileName, File file) throws Exception{
        Map<String,String> credentials = EnvFileManager.readEnvFile();

        if(credentials.get("EMAIL").trim().isEmpty() || credentials.get("PASSWORD").trim().isEmpty()){
            throw new CredentialsNotFound();
        }

        System.out.println(credentials.get("EMAIL").trim() + " " + credentials.get("PASSWORD").trim());

        if(!isValidEmail(receiver)){
            throw new InvalidEmailFormat();
        }

         Email email =  EmailBuilder.startingBlank().from("Gestore Seriali - ECO PC", credentials.get("EMAIL").trim())
                .to("ECO-PC", receiver)
                .withSubject(subject)
                .withPlainText(body)
                .withAttachment(fileName, new FileDataSource(file))
                .buildEmail();

        Mailer mailer = MailerBuilder.withSMTPServer("smtp.gmail.com", 587, credentials.get("EMAIL").trim(), credentials.get("PASSWORD").trim())
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();

        mailer.sendMail(email);
        System.out.println("Email sent successfully");
    }

    private static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
