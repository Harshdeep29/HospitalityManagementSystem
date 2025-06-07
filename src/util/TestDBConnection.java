package util;

import db.DatabaseConnector;
import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection conn = DatabaseConnector.getConnection();
        if (conn != null) {
            System.out.println("✅ Database connection successful!");
        } else {
            System.out.println("❌ Failed to connect.");
        }
    }
}
