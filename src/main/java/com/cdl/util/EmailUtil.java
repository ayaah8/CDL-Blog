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
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });
    }

    // Méthode pour le mot de passe oublié (2 arguments)
    public static boolean sendPasswordResetEmail(String recipientEmail, String resetLink) {
        return sendEmail(recipientEmail, "Réinitialisation de mot de passe", 
            "Bonjour, \n\nVoici votre lien de réinitialisation : " + resetLink);
    }

    // Méthode pour l'inscription (3 arguments pour corriger l'erreur de compilation)
    public static boolean sendVerificationEmail(String recipientEmail, String code, String username) {
        return sendEmail(recipientEmail, "Vérification de votre compte", 
            "Bonjour " + username + ", \n\nVotre code de vérification est : " + code);
    }

    private static boolean sendEmail(String recipientEmail, String subject, String content) {
        if (SENDER_EMAIL == null || SENDER_PASSWORD == null) return false;
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
