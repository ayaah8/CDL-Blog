package com.cdl.util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class EmailUtil {

    // On récupère les variables de Railway
    private static final String SENDER_EMAIL = System.getenv("SENDER_EMAIL");
    private static final String BREVO_API_KEY = System.getenv("SENDER_PASSWORD");

    public static boolean sendPasswordResetEmail(String recipientEmail, String resetLink) {
        return sendEmailViaApi(recipientEmail, "Réinitialisation de mot de passe", 
            "Bonjour, <br><br>Voici votre lien de réinitialisation : " + resetLink);
    }

    public static boolean sendVerificationEmail(String recipientEmail, String code, String username) {
        return sendEmailViaApi(recipientEmail, "Vérification de votre compte", 
            "Bonjour " + username + ", <br><br>Votre code de vérification est : <b>" + code + "</b>");
    }

    // NOUVELLE MÉTHODE : Envoi via l'API Web de Brevo (100% garanti de passer Railway)
    private static boolean sendEmailViaApi(String recipientEmail, String subject, String htmlContent) {
        if (SENDER_EMAIL == null || BREVO_API_KEY == null) return false;

        try {
            URL url = new URL("https://api.brevo.com/v3/smtp/email");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            // On utilise la clé API stockée dans Railway
            conn.setRequestProperty("api-key", BREVO_API_KEY);
            conn.setDoOutput(true);

            // Création du format JSON attendu par Brevo
            String jsonInputString = "{"
                + "\"sender\": {\"name\": \"Support CDL Blog\", \"email\": \"" + SENDER_EMAIL + "\"},"
                + "\"to\": [{\"email\": \"" + recipientEmail + "\"}],"
                + "\"subject\": \"" + subject + "\","
                + "\"htmlContent\": \"" + htmlContent + "\""
                + "}";

            // Envoi de la requête
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            // 200 ou 201 signifie que Brevo a bien envoyé l'email !
            return (responseCode == 200 || responseCode == 201);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
