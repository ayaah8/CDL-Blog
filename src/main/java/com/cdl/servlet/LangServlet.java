package com.cdl.servlet;

import jakarta.servlet.http.*;
import java.io.IOException;

public class LangServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lang = req.getParameter("lang");
        if ("fr".equals(lang) || "en".equals(lang)) {
            req.getSession(true).setAttribute("lang", lang);
        }
        String ref = req.getHeader("Referer");
        resp.sendRedirect(ref != null ? ref : req.getContextPath() + "/");
    }
}
