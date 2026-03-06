package com.cdl.servlet;

import com.cdl.dao.ArticleDAO;
import com.cdl.dao.CommentDAO;
import com.cdl.model.Article;
import com.cdl.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ArticleServlet extends HttpServlet {
    private final ArticleDAO articleDAO = new ArticleDAO();
    private final CommentDAO commentDAO = new CommentDAO();
    private static final int PAGE_SIZE = 6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        String path   = req.getServletPath();

        if ("view".equals(action)) {
            showArticle(req, resp);
        } else if ("new".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/jsp/article-form.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("article", articleDAO.findById(id));
            req.getRequestDispatcher("/WEB-INF/jsp/article-form.jsp").forward(req, resp);
        } else if ("delete".equals(action)) {
            deleteArticle(req, resp);
        } else if (path.startsWith("/member")) {
            showMyArticles(req, resp);
        } else {
            listArticles(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("save".equals(req.getParameter("action"))) saveArticle(req, resp);
    }

    private void listArticles(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int page = 1;
        try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}
        req.setAttribute("articles",    articleDAO.findPublished(page, PAGE_SIZE));
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages",  (int) Math.ceil((double) articleDAO.countPublished() / PAGE_SIZE));
        req.getRequestDispatcher("/WEB-INF/jsp/articles.jsp").forward(req, resp);
    }

    private void showArticle(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Article a = articleDAO.findById(id);
        if (a == null) { resp.sendError(404); return; }
        a.setComments(commentDAO.findByArticle(id));
        req.setAttribute("article", a);
        req.getRequestDispatcher("/WEB-INF/jsp/article-view.jsp").forward(req, resp);
    }

    private void deleteArticle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = (User) req.getSession().getAttribute("user");
        Article a = articleDAO.findById(id);
        if (a != null && (a.getAuthorId() == user.getId() || user.isAdmin())) {
            articleDAO.delete(id);
        }
        resp.sendRedirect(req.getContextPath() + "/member/articles");
    }

    private void saveArticle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        String idStr = req.getParameter("id");
        Article article;
        if (idStr != null && !idStr.isBlank()) {
            article = articleDAO.findById(Integer.parseInt(idStr));
        } else {
            article = new Article(); article.setAuthorId(user.getId());
        }
        article.setTitle(req.getParameter("title").trim());
        article.setContent(req.getParameter("content").trim());
        article.setSummary(req.getParameter("summary"));
        article.setStatus(req.getParameter("status") != null ? req.getParameter("status") : "PUBLISHED");
        String cat = req.getParameter("categoryId");
        if (cat != null && !cat.isBlank()) article.setCategoryId(Integer.parseInt(cat));

        if (article.getId() == 0) articleDAO.create(article);
        else articleDAO.update(article);
        resp.sendRedirect(req.getContextPath() + "/member/articles");
    }

    private void showMyArticles(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("articles", articleDAO.findByAuthor(user.getId()));
        req.getRequestDispatcher("/WEB-INF/jsp/my-articles.jsp").forward(req, resp);
    }
}
