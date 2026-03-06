package com.cdl.util;

import java.sql.*;

public class DBUtil {

    private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASS;

    static {
        try {
            // Le driver MySQL pour les versions récentes (Connector/J 8+)
            DRIVER = "com.mysql.cj.jdbc.Driver";
            
            // On récupère les informations de connexion depuis les variables Railway
            URL    = System.getenv("MYSQL_URL");
            USER   = System.getenv("MYSQLUSER");
            PASS   = System.getenv("MYSQLPASSWORD");
            
            // Chargement du driver
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("DBUtil init failed: " + e.getMessage());
        }
    }

    /**
     * Établit la connexion à la base de données Railway
     */
    public static Connection getConnection() throws SQLException {
        if (URL == null || USER == null || PASS == null) {
            throw new SQLException("Les variables d'environnement MySQL ne sont pas configurées sur Railway.");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Ferme les ressources JDBC (ResultSet, Statement, Connection)
     */
    public static void close(Connection c, Statement s, ResultSet r) {
        try { if (r != null) r.close(); } catch (SQLException ignored) {}
        try { if (s != null) s.close(); } catch (SQLException ignored) {}
        try { if (c != null) c.close(); } catch (SQLException ignored) {}
    }

    public static void close(Connection c, Statement s) { 
        close(c, s, null); 
    }
}