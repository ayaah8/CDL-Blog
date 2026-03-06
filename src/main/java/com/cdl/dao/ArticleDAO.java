package com.cdl.dao;

import com.cdl.model.Article;
import com.cdl.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    private static final String SELECT_BASE =
        "SELECT a.*, u.username AS author_username, u.avatar_url AS author_avatar," +
        " c.name_fr, c.name_en FROM articles a" +
        " LEFT JOIN users u ON a.author_id=u.id" +
        " LEFT JOIN categories c ON a.category_id=c.id";

    public boolean create(Article a) {
        String sql = "INSERT INTO articles(title,content,summary,author_id,category_id,status) VALUES(?,?,?,?,?,?)";
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection();
            s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            s.setString(1, a.getTitle()); s.setString(2, a.getContent());
            s.setString(3, a.getSummary()); s.setInt(4, a.getAuthorId());
            if (a.getCategoryId() != null) s.setInt(5, a.getCategoryId());
            else s.setNull(5, Types.INTEGER);
            s.setString(6, a.getStatus() != null ? a.getStatus() : "PUBLISHED");
            int rows = s.executeUpdate();
            if (rows > 0) { ResultSet k = s.getGeneratedKeys(); if (k.next()) a.setId(k.getInt(1)); }
            return rows > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { DBUtil.close(c, s); }
    }

    public boolean update(Article a) {
        String sql = "UPDATE articles SET title=?,content=?,summary=?,category_id=?,status=?,updated_at=NOW() WHERE id=?";
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setString(1, a.getTitle()); s.setString(2, a.getContent());
            s.setString(3, a.getSummary());
            if (a.getCategoryId() != null) s.setInt(4, a.getCategoryId());
            else s.setNull(4, Types.INTEGER);
            s.setString(5, a.getStatus()); s.setInt(6, a.getId());
            return s.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { DBUtil.close(c, s); }
    }

    public boolean delete(int id) {
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection();
            s = c.prepareStatement("DELETE FROM articles WHERE id=?");
            s.setInt(1, id); return s.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { DBUtil.close(c, s); }
    }

    public Article findById(int id) {
        String sql = SELECT_BASE + " WHERE a.id=?";
        Connection c = null; PreparedStatement s = null; ResultSet r = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setInt(1, id); r = s.executeQuery();
            if (r.next()) { incrementViews(id); return map(r); }
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s, r); }
        return null;
    }

    public List<Article> findPublished(int page, int size) {
        String sql = SELECT_BASE + " WHERE a.status='PUBLISHED' ORDER BY a.created_at DESC LIMIT ? OFFSET ?";
        return listQuery(sql, page, size);
    }

    public List<Article> findByAuthor(int authorId) {
        String sql = SELECT_BASE + " WHERE a.author_id=? ORDER BY a.created_at DESC";
        List<Article> list = new ArrayList<>();
        Connection c = null; PreparedStatement s = null; ResultSet r = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setInt(1, authorId); r = s.executeQuery();
            while (r.next()) list.add(map(r));
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s, r); }
        return list;
    }

    public int countPublished() {
        Connection c = null; Statement s = null; ResultSet r = null;
        try {
            c = DBUtil.getConnection(); s = c.createStatement();
            r = s.executeQuery("SELECT COUNT(*) FROM articles WHERE status='PUBLISHED'");
            if (r.next()) return r.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s, r); }
        return 0;
    }

    private List<Article> listQuery(String sql, int page, int size) {
        List<Article> list = new ArrayList<>();
        Connection c = null; PreparedStatement s = null; ResultSet r = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setInt(1, size); s.setInt(2, (page - 1) * size);
            r = s.executeQuery();
            while (r.next()) list.add(map(r));
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s, r); }
        return list;
    }

    private void incrementViews(int id) {
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection();
            s = c.prepareStatement("UPDATE articles SET views=views+1 WHERE id=?");
            s.setInt(1, id); s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s); }
    }

    private Article map(ResultSet r) throws SQLException {
        Article a = new Article();
        a.setId(r.getInt("id"));
        a.setTitle(r.getString("title"));
        a.setContent(r.getString("content"));
        a.setSummary(r.getString("summary"));
        a.setAuthorId(r.getInt("author_id"));
        a.setAuthorUsername(r.getString("author_username"));
        a.setAuthorAvatar(r.getString("author_avatar"));
        int catId = r.getInt("category_id");
        if (!r.wasNull()) a.setCategoryId(catId);
        a.setStatus(r.getString("status"));
        a.setViews(r.getInt("views"));
        Timestamp cr = r.getTimestamp("created_at");
        if (cr != null) a.setCreatedAt(cr.toLocalDateTime());
        Timestamp up = r.getTimestamp("updated_at");
        if (up != null) a.setUpdatedAt(up.toLocalDateTime());
        // Category name (lang resolved in JSP)
        String nameFr = r.getString("name_fr");
        if (nameFr != null) a.setCategoryName(nameFr);
        return a;
    }
}
