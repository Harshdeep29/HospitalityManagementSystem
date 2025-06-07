package db;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnector {
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = DatabaseConnector.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                url = prop.getProperty("db.url");
                user = prop.getProperty("db.user");
                password = prop.getProperty("db.password");
            } else {
                System.out.println("⚠️ db.properties not found.");
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
