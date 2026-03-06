package com.cdl.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {

    private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASS;

    static {
        try (InputStream in = DBUtil.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            Properties p = new Properties();
            p.load(in);
            DRIVER = p.getProperty("db.driver");
            URL    = p.getProperty("db.url");
            USER   = p.getProperty("db.username");
            PASS   = p.getProperty("db.password");
            Class.forName(DRIVER);
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError("DBUtil init failed: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void close(Connection c, Statement s, ResultSet r) {
        try { if (r != null) r.close(); } catch (SQLException ignored) {}
        try { if (s != null) s.close(); } catch (SQLException ignored) {}
        try { if (c != null) c.close(); } catch (SQLException ignored) {}
    }

    public static void close(Connection c, Statement s) { close(c, s, null); }
}
