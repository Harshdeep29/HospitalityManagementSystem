package gui;

import dao.UserDAO;
import db.DatabaseConnector;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;

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
        signUpButton = new JButton("Sign Up");
        panel.add(loginButton);
        panel.add(signUpButton);

        add(panel);
        setVisible(true);

        // Action
        loginButton.addActionListener(e -> attemptLogin());

        signUpButton.addActionListener(e -> attemptSignup());
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

    private void attemptSignup() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        String[] roles = {"admin", "reception"};
        JComboBox<String> roleDropdown = new JComboBox<>(roles);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleDropdown);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sign up", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String role = roleDropdown.getSelectedItem().toString();

            if(username == null || password == null) {
                JOptionPane.showMessageDialog(this, "Username and password are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                User newUser = new User(username, password, role);
                UserDAO userDAO = new UserDAO(DatabaseConnector.getConnection());
                boolean success = userDAO.addUser(newUser);

                if (success) {
                    JOptionPane.showMessageDialog(this, "New user "+username+" successfully added as " + newUser.getRole());
                }
                else {
                    JOptionPane.showMessageDialog(this, "Failed to create user, Username may already exist.");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Error occured while trying to save the user");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm());
    }
}