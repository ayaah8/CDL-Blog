package com.cdl.dao;

import com.cdl.model.Comment;
import com.cdl.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public boolean create(Comment cm) {
        String sql = "INSERT INTO comments(content,article_id,author_id) VALUES(?,?,?)";
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection();
            s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            s.setString(1, cm.getContent());
            s.setInt(2, cm.getArticleId());
            s.setInt(3, cm.getAuthorId());
            int rows = s.executeUpdate();
            if (rows > 0) { ResultSet k = s.getGeneratedKeys(); if (k.next()) cm.setId(k.getInt(1)); }
            return rows > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { DBUtil.close(c, s); }
    }

    public boolean delete(int id) {
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection();
            s = c.prepareStatement("DELETE FROM comments WHERE id=?");
            s.setInt(1, id); return s.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { DBUtil.close(c, s); }
    }

    public Comment findById(int id) {
        String sql = "SELECT cm.*,u.username AS author_username,u.avatar_url AS author_avatar" +
                     " FROM comments cm LEFT JOIN users u ON cm.author_id=u.id WHERE cm.id=?";
        Connection c = null; PreparedStatement s = null; ResultSet r = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setInt(1, id); r = s.executeQuery();
            if (r.next()) return map(r);
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s, r); }
        return null;
    }

    public List<Comment> findByArticle(int articleId) {
        String sql = "SELECT cm.*,u.username AS author_username,u.avatar_url AS author_avatar" +
                     " FROM comments cm LEFT JOIN users u ON cm.author_id=u.id" +
                     " WHERE cm.article_id=? ORDER BY cm.created_at ASC";
        List<Comment> list = new ArrayList<>();
        Connection c = null; PreparedStatement s = null; ResultSet r = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setInt(1, articleId); r = s.executeQuery();
            while (r.next()) list.add(map(r));
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s, r); }
        return list;
    }

    private Comment map(ResultSet r) throws SQLException {
        Comment cm = new Comment();
        cm.setId(r.getInt("id"));
        cm.setContent(r.getString("content"));
        cm.setArticleId(r.getInt("article_id"));
        cm.setAuthorId(r.getInt("author_id"));
        cm.setAuthorUsername(r.getString("author_username"));
        cm.setAuthorAvatar(r.getString("author_avatar"));
        Timestamp cr = r.getTimestamp("created_at");
        if (cr != null) cm.setCreatedAt(cr.toLocalDateTime());
        return cm;
    }
}
