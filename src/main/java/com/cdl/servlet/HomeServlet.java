package com.cdl.servlet;

import com.cdl.dao.ArticleDAO;
import com.cdl.model.Article;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; // 1. IMPORT OBLIGATOIRE
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

// 2. C'EST CETTE LIGNE QUI FAIT LE LIEN AVEC TON <jsp:forward page="/home"/>
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    private final ArticleDAO dao = new ArticleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // On récupère les articles de la base de données
        List<Article> articles = dao.findPublished(1, 6);
        
        // On les transmet à ta vue (le grand fichier index.jsp)
        req.setAttribute("articles", articles);
        
        // On affiche la page
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }
}