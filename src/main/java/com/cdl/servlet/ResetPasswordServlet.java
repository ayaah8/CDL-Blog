package com.cdl.servlet;

import com.cdl.dao.UserDAO;
import com.cdl.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.time.LocalDateTime;

public class ResetPasswordServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = userDAO.findByToken(token);

        if (user == null || user.getTokenExpiry() == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            req.setAttribute("error", "auth.reset.error.invalid");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }
        
        req.setAttribute("token", token);
        req.getRequestDispatcher("/WEB-INF/jsp/reset-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm");

        if (password == null || !password.equals(confirm)) {
            req.setAttribute("error", "auth.reset.error.match");
            req.setAttribute("token", token);
            req.getRequestDispatcher("/WEB-INF/jsp/reset-password.jsp").forward(req, resp);
            return;
        }

        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        if (userDAO.updatePassword(token, hash)) {
            req.setAttribute("success", "auth.reset.success");
        } else {
            req.setAttribute("error", "auth.reset.error.general");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }
}