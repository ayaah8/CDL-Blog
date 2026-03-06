package com.cdl.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailUtil {

    // ⚠️ À MODIFIER : Remplace par ton adresse email et ton mot de passe d'application
    private static final String SENDER_EMAIL = "cdl.blog.app@gmail.com"; 
    private static final String SENDER_PASSWORD = "ablaterttayicoql"; 

    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });
    }

    public static boolean sendVerificationEmail(String toEmail, String username, String link) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(SENDER_EMAIL, "CDL Blog"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Vérification de votre compte CDL Blog ✔️");

            String htmlContent = "<h2 style='color: #7c3aed;'>Bienvenue " + username + " !</h2>"
                    + "<p>Merci de t'être inscrit sur CDL Blog. Pour activer ton compte, clique sur le bouton ci-dessous :</p>"
                    + "<a href='" + link + "' style='background-color: #7c3aed; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;'>Vérifier mon compte</a>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendPasswordResetEmail(String toEmail, String resetLink) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(SENDER_EMAIL, "CDL Blog"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Réinitialisation de votre mot de passe 🔒");

            String htmlContent = "<h2 style='color: #7c3aed;'>Mot de passe oublié ?</h2>"
                    + "<p>Vous avez demandé à réinitialiser votre mot de passe. Cliquez sur le bouton ci-dessous :</p>"
                    + "<a href='" + resetLink + "' style='background-color: #7c3aed; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;'>Réinitialiser mon mot de passe</a>"
                    + "<p><br>Si vous n'avez pas fait cette demande, ignorez cet email. Ce lien expirera dans 1 heure.</p>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}