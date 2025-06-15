package gui;

import dao.UserDAO;
import db.DatabaseConnector;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginForm() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // GUI Layout
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(loginButton);

        add(panel);
        setVisible(true);

        // Action
        loginButton.addActionListener(e -> attemptLogin());
    }

    private void attemptLogin() {
        try {
            Connection conn = DatabaseConnector.getConnection();  // Your DB utility
            UserDAO userDAO = new UserDAO(conn);
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            User authenticated = userDAO.validateUser(user, pass);
            if (authenticated != null) {
                JOptionPane.showMessageDialog(this, "Login successful as " + authenticated.getRole());
                dispose(); // Close login form

                if ("admin".equals(authenticated.getRole())) {
                    new AdminFrame(user);  // Full access
                }else {
                    new ReceptionFrame(user);  // Create limited access frame
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm());
    }
}
