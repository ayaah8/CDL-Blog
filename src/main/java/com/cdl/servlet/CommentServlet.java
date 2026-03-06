package com.cdl.servlet;

import com.cdl.dao.CommentDAO;
import com.cdl.model.Comment;
import com.cdl.model.User;
import jakarta.servlet.http.*;
import java.io.IOException;

public class CommentServlet extends HttpServlet {
    private final CommentDAO dao = new CommentDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            int articleId = Integer.parseInt(req.getParameter("articleId"));
            dao.create(new Comment(req.getParameter("content").trim(), articleId, user.getId()));
            resp.sendRedirect(req.getContextPath() + "/articles?action=view&id=" + articleId);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Comment cm = dao.findById(id);
            if (cm != null && (cm.getAuthorId() == user.getId() || user.isAdmin())) {
                dao.delete(id);
                resp.sendRedirect(req.getContextPath() + "/articles?action=view&id=" + cm.getArticleId());
            } else {
                resp.sendError(403);
            }
        }
    }
}
