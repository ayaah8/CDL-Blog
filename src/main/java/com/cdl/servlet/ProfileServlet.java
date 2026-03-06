package com.cdl.servlet;

import com.cdl.dao.UserDAO;
import com.cdl.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        if ("updateProfile".equals(action)) {
            String newUsername = req.getParameter("username").trim();
            if (!newUsername.equals(user.getUsername()) && dao.usernameExists(newUsername)) {
                req.setAttribute("error", "auth.register.error.username");
                req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp); return;
            }
            user.setUsername(newUsername);
            user.setBio(req.getParameter("bio"));
            dao.update(user);
            req.getSession().setAttribute("user", user);
            req.setAttribute("success", "general.success");
            req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);

        } else if ("changePassword".equals(action)) {
            String oldPwd = req.getParameter("oldPassword");
            String newPwd = req.getParameter("newPassword");
            String conf   = req.getParameter("confirmPassword");

            if (!BCrypt.checkpw(oldPwd, user.getPasswordHash())) {
                req.setAttribute("pwdError", "Mot de passe actuel incorrect.");
                req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp); return;
            }
            if (!newPwd.equals(conf)) {
                req.setAttribute("pwdError", "Les nouveaux mots de passe ne correspondent pas.");
                req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp); return;
            }
            String hash = BCrypt.hashpw(newPwd, BCrypt.gensalt(12));
            dao.updatePassword(user.getId(), hash);
            user.setPasswordHash(hash);
            req.getSession().setAttribute("user", user);
            req.setAttribute("pwdSuccess", "Mot de passe modifié avec succès.");
            req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
        }
    }
}
