package com.cdl.servlet;

import com.cdl.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class VerifyServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String token = req.getParameter("token");
        if (token != null && dao.verifyEmail(token)) {
            req.setAttribute("success", "auth.verify.success");
        } else {
            req.setAttribute("error", "auth.verify.error");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }
}