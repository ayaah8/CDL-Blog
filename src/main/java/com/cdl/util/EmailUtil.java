package com.cdl.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

    private static final String SENDER_EMAIL = System.getenv("SENDER_EMAIL");
    private static final String SENDER_PASSWORD = System.getenv("SENDER_PASSWORD");

    private static Session getSession() {
        Properties props = new Properties();
        // Configuration optimisée pour Railway -> Gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        
        // Protocoles de sécurité Jakarta EE 11
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "jakarta.net.ssl.SSLSocketFactory");
        
        // Timeouts stricts pour éviter le blocage du thread Tomcat
        props.put("mail.smtp.connectiontimeout", "10000"); // 10 secondes
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Utilise les variables d'environnement configurées sur Railway
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });
    }

    public static boolean sendPasswordResetEmail(String recipientEmail, String resetLink) {
        if (SENDER_EMAIL == null || SENDER_PASSWORD == null) {
            System.err.println("ERREUR : Les variables d'environnement SENDER_EMAIL ou SENDER_PASSWORD sont vides !");
            return false;
        }

        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Réinitialisation de votre mot de passe");
            message.setText("Bonjour, \n\nVoici votre lien de réinitialisation : " + resetLink);

            Transport.send(message);
            return true;
        } catch (Exception e) {
            // Affiche l'erreur précise dans les logs de Railway
            e.printStackTrace();
            return false;
        }
    }
}
