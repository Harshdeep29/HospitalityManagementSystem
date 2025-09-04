package dao;

import model.User;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String query = "INSERT INTO users(username,password,role) VALUES(?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getRole());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
            return false;
        }
    }

    public User validateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(storedHashedPassword);
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        }
        return null; // Invalid login
    }
}
