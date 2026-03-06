package com.cdl.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailUtil {

    // ⚠️ L'application ira chercher ces valeurs dans les Variables Railway
    private static final String SENDER_EMAIL = System.getenv("SENDER_EMAIL");
    private static final String SENDER_PASSWORD = System.getenv("SENDER_PASSWORD");

    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        
        // Configuration SSL pour Railway (Port 465)
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        
        // 🛑 AJOUT CRUCIAL : Limite de temps (Timeouts) pour éviter le chargement infini
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