package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RetrieveDataForm extends JFrame {
    private final MainFrame mainFrame;

    public RetrieveDataForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setTitle("Retrieve Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Hotels", createTablePanel(
                new String[]{"Hotel ID", "Name", "Location"},
                new Object[][]{
                        {"H001", "Taj", "Delhi"},
                        {"H002", "Oberoi", "Mumbai"}
                }
        ));

        tabbedPane.addTab("Rooms", createTablePanel(
                new String[]{"Room ID", "Hotel ID", "Type", "Price", "Status"},
                new Object[][]{
                        {"R101", "H001", "Deluxe", "4000", "Available"},
                        {"R102", "H002", "Standard", "3000", "Booked"}
                }
        ));

        tabbedPane.addTab("Guests", createTablePanel(
                new String[]{"Guest ID", "Name", "Email", "Phone"},
                new Object[][]{
                        {"G001", "John Doe", "john@example.com", "1234567890"},
                        {"G002", "Jane Smith", "jane@example.com", "9876543210"}
                }
        ));

        tabbedPane.addTab("Reservations", createTablePanel(
                new String[]{"Reservation ID", "Guest ID", "Room ID", "Check-In", "Check-Out"},
                new Object[][]{
                        {"RES001", "G001", "R101", "2024-05-10", "2024-05-12"}
                }
        ));

        add(tabbedPane, BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            this.dispose();
            this.mainFrame.setVisible(true);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(back);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createTablePanel(String[] columns, Object[][] data) {
        JTable table = new JTable(new DefaultTableModel(data, columns));
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
