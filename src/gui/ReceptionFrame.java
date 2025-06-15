package gui;

import javax.swing.*;
import java.awt.*;

public class ReceptionFrame extends JFrame {
    private String username;
    public ReceptionFrame(String username) {
        this.username = username;

        setTitle("Reception Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Top Menu Bar with Logout
        JMenuBar menuBar = new JMenuBar();
        JMenu accountMenu = new JMenu("Account");
        JMenuItem logoutItem = new JMenuItem("Logout");

        logoutItem.addActionListener(e -> {
            dispose(); // close current window
            new LoginForm(); // go back to login screen
        });

        accountMenu.add(logoutItem);
        menuBar.add(accountMenu);
        setJMenuBar(menuBar);

        JLabel title = new JLabel("Welcome " + username + " to Admin Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Buttons in center
        JButton guestButton = new JButton("Manage Guests");
        JButton reservationButton = new JButton("Manage Reservations");

        guestButton.setPreferredSize(new Dimension(200, 60));
        reservationButton.setPreferredSize(new Dimension(200, 60));

        guestButton.addActionListener(e -> {
            this.setVisible(false);
            new GuestForm(this);
        });

        reservationButton.addActionListener(e -> new ReservationForm());

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(guestButton, gbc);

        gbc.gridy = 1;
        panel.add(reservationButton, gbc);

        add(panel);
        setVisible(true);
    }
}
