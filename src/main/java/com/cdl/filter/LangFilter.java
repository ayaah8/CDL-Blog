package com.cdl.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LangFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(true);
        if (session.getAttribute("lang") == null) {
            session.setAttribute("lang", "fr");
        }
        String lang = (String) session.getAttribute("lang");
        Locale locale = "en".equals(lang) ? Locale.ENGLISH : Locale.FRENCH;
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        request.setAttribute("msg", bundle);
        request.setAttribute("lang", lang);
        chain.doFilter(req, res);
    }
}
