package com.cdl.servlet;

import com.cdl.dao.UserDAO;
import com.cdl.model.User;
import com.cdl.util.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String email    = req.getParameter("email");
        String password = req.getParameter("password");
        String confirm  = req.getParameter("confirm");

        if (username != null) username = username.trim();
        if (email != null) email = email.trim().toLowerCase();

        if (username == null || username.isEmpty() || email == null || email.isEmpty()) {
            req.setAttribute("error", "auth.register.error.fields");
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
            return;
        }
        if (userDAO.emailExists(email)) {
            req.setAttribute("error", "auth.register.error.email");
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
            return;
        }
        if (userDAO.usernameExists(username)) {
            req.setAttribute("error", "auth.register.error.username");
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
            return;
        }
        if (password == null || !password.equals(confirm)) {
            req.setAttribute("error", "auth.register.error.password");
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
            return;
        }

        String hash  = BCrypt.hashpw(password, BCrypt.gensalt(12));
        String token = UUID.randomUUID().toString();
        User user = new User(username, email, hash);
        user.setVerificationToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusHours(24));

        if (!userDAO.create(user)) {
            req.setAttribute("error", "general.error");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        // Lien de vérification : URL complète pour que le clic dans l'email fonctionne
        // Si votre servlet est mappée sur /VerifyServlet, mettez "/VerifyServlet" au lieu de "/verify"
        String scheme   = req.getScheme();
        String host     = req.getServerName();
        int port       = req.getServerPort();
        String contextPath = req.getContextPath();
        String portPart = (port == 80 || port == 443) ? "" : (":" + port);
        String verificationLink = scheme + "://" + host + portPart + contextPath + "/verify?token=" + token;

        boolean emailSent = EmailUtil.sendVerificationEmail(email, username, verificationLink);

        if (emailSent) {
            req.setAttribute("success", "Un email de vérification a été envoyé. Veuillez vérifier votre boîte de réception.");
        } else {
            req.setAttribute("error", "Le compte a été créé, mais l'envoi de l'email a échoué.");
            userDAO.verifyEmail(token);
        }

        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }
}