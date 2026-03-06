package com.cdl.servlet;

import com.cdl.dao.UserDAO;
import com.cdl.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s != null && s.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/articles"); return;
        }
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email    = req.getParameter("email").trim().toLowerCase();
        String password = req.getParameter("password");
        User user = dao.findByEmail(email);
        if (user == null || !BCrypt.checkpw(password, user.getPasswordHash())) {
            req.setAttribute("error", "auth.login.error");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp); return;
        }
        if (!user.isVerified()) {
            req.setAttribute("error", "auth.login.notverified");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp); return;
        }
        req.getSession(true).setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/articles");
    }
}
