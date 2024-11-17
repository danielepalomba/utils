package org.app.mail;

import org.app.util.EnvFileManager;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.util.Map;

public class MailSender {

    public static void sendSerialsViaMail(String body, String subject, String receiver){
        Map<String,String> credentials = EnvFileManager.readEnvFile();

        Email email =  EmailBuilder.startingBlank().from("Gestore Seriali - ECOPC", credentials.get("EMAIL"))
                .to("EcoPc", receiver)
                .withSubject(subject)
                .withPlainText(body)
                .buildEmail();

        Mailer mailer = MailerBuilder.withSMTPServer("stmp.gmail.com", 587, credentials.get("EMAIL"), credentials.get("PASSWORD"))
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();

        mailer.sendMail(email);
        System.out.println("Email sent successfully");
    }
}
