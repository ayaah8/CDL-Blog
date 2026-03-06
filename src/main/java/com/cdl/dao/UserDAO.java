package com.cdl.dao;

import com.cdl.model.User;
import com.cdl.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;

public class UserDAO {

    public boolean create(User u) {
        String sql = "INSERT INTO users(username,email,password_hash,bio,avatar_url,role," +
                     "is_verified,verification_token,token_expiry) VALUES(?,?,?,?,?,?,?,?,?)";
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection();
            s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            s.setString(1, u.getUsername());
            s.setString(2, u.getEmail());
            s.setString(3, u.getPasswordHash());
            s.setString(4, u.getBio());
            s.setString(5, u.getAvatarUrl() != null ? u.getAvatarUrl() : "default-avatar.png");
            s.setString(6, u.getRole()     != null ? u.getRole()      : "MEMBER");
            s.setBoolean(7, u.isVerified());
            s.setString(8, u.getVerificationToken());
            s.setTimestamp(9, u.getTokenExpiry() != null ? Timestamp.valueOf(u.getTokenExpiry()) : null);
            int rows = s.executeUpdate();
            if (rows > 0) { ResultSet k = s.getGeneratedKeys(); if (k.next()) u.setId(k.getInt(1)); }
            return rows > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { DBUtil.close(c, s); }
    }

    public User findByEmail(String email) {
        return findBy("email", email);
    }

    public User findByUsername(String username) {
        return findBy("username", username);
    }

    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id=?";
        Connection c = null; PreparedStatement s = null; ResultSet r = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setInt(1, id); r = s.executeQuery();
            if (r.next()) return map(r);
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s, r); }
        return null;
    }

    public boolean verifyEmail(String token) {
        String sql = "UPDATE users SET is_verified=1,verification_token=NULL,token_expiry=NULL " +
                     "WHERE verification_token=? AND token_expiry > NOW()";
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setString(1, token);
            return s.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { DBUtil.close(c, s); }
    }

    public boolean update(User u) {
        String sql = "UPDATE users SET username=?,bio=?,avatar_url=?,updated_at=NOW() WHERE id=?";
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setString(1, u.getUsername()); s.setString(2, u.getBio());
            s.setString(3, u.getAvatarUrl()); s.setInt(4, u.getId());
            return s.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { DBUtil.close(c, s); }
    }

    public boolean updatePassword(int userId, String hash) {
        String sql = "UPDATE users SET password_hash=?,updated_at=NOW() WHERE id=?";
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setString(1, hash); s.setInt(2, userId);
            return s.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { DBUtil.close(c, s); }
    }

    public boolean emailExists(String email)       { return findByEmail(email)    != null; }
    public boolean usernameExists(String username) { return findByUsername(username) != null; }

    private User findBy(String col, String val) {
        String sql = "SELECT * FROM users WHERE " + col + "=?";
        Connection c = null; PreparedStatement s = null; ResultSet r = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setString(1, val); r = s.executeQuery();
            if (r.next()) return map(r);
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s, r); }
        return null;
    }

    private User map(ResultSet r) throws SQLException {
        User u = new User();
        u.setId(r.getInt("id"));
        u.setUsername(r.getString("username"));
        u.setEmail(r.getString("email"));
        u.setPasswordHash(r.getString("password_hash"));
        u.setBio(r.getString("bio"));
        u.setAvatarUrl(r.getString("avatar_url"));
        u.setRole(r.getString("role"));
        u.setVerified(r.getBoolean("is_verified"));
        u.setVerificationToken(r.getString("verification_token"));
        Timestamp exp = r.getTimestamp("token_expiry");
        if (exp != null) u.setTokenExpiry(exp.toLocalDateTime());
        Timestamp cr = r.getTimestamp("created_at");
        if (cr != null) u.setCreatedAt(cr.toLocalDateTime());
        return u;
    }

    // --- NOUVELLES MÉTHODES POUR MOT DE PASSE OUBLIÉ ---

    public boolean setResetToken(String email, String token) {
        String sql = "UPDATE users SET verification_token=?, token_expiry=? WHERE email=?";
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setString(1, token);
            s.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
            s.setString(3, email);
            return s.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s); }
        return false;
    }

    public User findByToken(String token) {
        return findBy("verification_token", token);
    }

    public boolean updatePassword(String token, String newPasswordHash) {
        String sql = "UPDATE users SET password_hash=?, verification_token=NULL, token_expiry=NULL WHERE verification_token=?";
        Connection c = null; PreparedStatement s = null;
        try {
            c = DBUtil.getConnection(); s = c.prepareStatement(sql);
            s.setString(1, newPasswordHash);
            s.setString(2, token);
            return s.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        finally { DBUtil.close(c, s); }
        return false;
    }
}