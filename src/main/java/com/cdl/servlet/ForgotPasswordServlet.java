package com.cdl.servlet;

import com.cdl.dao.UserDAO;
import com.cdl.util.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

public class ForgotPasswordServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        if (email != null) email = email.trim().toLowerCase();

        if (email != null && userDAO.emailExists(email)) {
            String token = UUID.randomUUID().toString();
            userDAO.setResetToken(email, token);

            String scheme = req.getScheme();
            String host = req.getServerName();
            int port = req.getServerPort();
            String portPart = (port == 80 || port == 443) ? "" : (":" + port);
            String resetLink = scheme + "://" + host + portPart + req.getContextPath() + "/reset-password?token=" + token;

            EmailUtil.sendPasswordResetEmail(email, resetLink);
        }
        
        // Utilisation de la clé de traduction
        req.setAttribute("success", "auth.forgot.success");
        req.getRequestDispatcher("/WEB-INF/jsp/forgot-password.jsp").forward(req, resp);
    }
}