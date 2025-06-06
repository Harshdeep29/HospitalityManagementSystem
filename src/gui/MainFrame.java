package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Hotel Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Welcome to Hotel Reservation System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Create buttons for each module
        JButton hotelButton = new JButton("Manage Hotels");
        JButton roomButton = new JButton("Manage Rooms");
        JButton guestButton = new JButton("Manage Guests");
        JButton reservationButton = new JButton("Manage Reservations");
        JButton retrieveDataButton = new JButton("Retrieve Data");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        hotelButton.addActionListener(e -> {
            new HotelForm(this);
            setVisible(false);
        });

        roomButton.addActionListener(e -> {
            new RoomForm(this);
            setVisible(false);
        });

        guestButton.addActionListener(e -> {
            new GuestForm(this);
            setVisible(false);
        });

        reservationButton.addActionListener(e -> {
            new ReservationForm(this);
            setVisible(false);
        });

        retrieveDataButton.addActionListener(e -> {
            new RetrieveDataForm(this); // Create a new form to show data
            setVisible(false);
        });

        exitButton.addActionListener(e -> System.exit(0));

        // Layout buttons in the center
        JPanel centerPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150));
        centerPanel.add(hotelButton);
        centerPanel.add(roomButton);
        centerPanel.add(guestButton);
        centerPanel.add(reservationButton);
        centerPanel.add(retrieveDataButton);
        centerPanel.add(exitButton);

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
