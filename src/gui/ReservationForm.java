package gui;

import dao.ReservationDAO;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReservationForm extends JFrame {
    private final MainFrame mainFrame;

    public ReservationForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setTitle("Reservation Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Reservation Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JButton newReservation = new JButton("New Reservation");
        JButton cancelReservation = new JButton("Cancel Reservation");
        JButton viewReservation = new JButton("View Reservation");

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        panel.add(newReservation);
        panel.add(cancelReservation);
        panel.add(viewReservation);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton back = new JButton("Back");
        JButton exit = new JButton("Exit");
        buttonPanel.add(back);
        buttonPanel.add(exit);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Reservation
        newReservation.addActionListener(e -> {
            JTextField guestId = new JTextField();
            JTextField roomId = new JTextField();
            JTextField checkIn = new JTextField();
            JTextField checkOut = new JTextField();
            JTextField totalPrice = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(5, 2));
            inputPanel.add(new JLabel("Guest ID:"));
            inputPanel.add(guestId);
            inputPanel.add(new JLabel("Room ID:"));
            inputPanel.add(roomId);
            inputPanel.add(new JLabel("Check-In Date (YYYY-MM-DD):"));
            inputPanel.add(checkIn);
            inputPanel.add(new JLabel("Check-Out Date (YYYY-MM-DD):"));
            inputPanel.add(checkOut);
            inputPanel.add(new JLabel("Total Price:"));
            inputPanel.add(totalPrice);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter Reservation Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int gId = Integer.parseInt(guestId.getText().trim());
                    int rId = Integer.parseInt(roomId.getText().trim());
                    String in = checkIn.getText().trim();
                    String out = checkOut.getText().trim();
                    double price = Double.parseDouble(totalPrice.getText().trim());

                    if (in.isEmpty() || out.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Dates cannot be empty.");
                        return;
                    }

                    if (!in.matches("\\d{4}-\\d{2}-\\d{2}") || !out.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        JOptionPane.showMessageDialog(this, "Dates must be in YYYY-MM-DD format.");
                        return;
                    }

                    if (price <= 0) {
                        JOptionPane.showMessageDialog(this, "Total price must be greater than 0.");
                        return;
                    }

                    boolean available = new ReservationDAO().isRoomAvailable(rId, in, out);
                    if (!available) {
                        JOptionPane.showMessageDialog(this, "❌ Room not available in this date range.");
                        return;
                    }

                    Reservation res = new Reservation(gId, rId, in, out, price);
                    boolean success = new ReservationDAO().addReservation(res);
                    JOptionPane.showMessageDialog(this, success ? "✅ Reservation added!" : "❌ Failed to add.");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Guest ID, Room ID, and Price must be numbers.");
                }

            }
        });

        // View Reservations
        viewReservation.addActionListener(e -> {
            List<Reservation> reservations = new ReservationDAO().getAllReservations();
            String[] colNames = {"ID", "Guest ID", "Room ID", "Check-In", "Check-Out", "Total Price"};
            Object[][] data = new Object[reservations.size()][6];
            for (int i = 0; i < reservations.size(); i++) {
                Reservation r = reservations.get(i);
                data[i][0] = r.getId();
                data[i][1] = r.getGuestId();
                data[i][2] = r.getRoomId();
                data[i][3] = r.getCheckInDate();
                data[i][4] = r.getCheckOutDate();
                data[i][5] = r.getTotalPrice();
            }

            JTable table = new JTable(data, colNames);
            JOptionPane.showMessageDialog(this, new JScrollPane(table), "All Reservations", JOptionPane.INFORMATION_MESSAGE);
        });

        // Cancel Reservation
        cancelReservation.addActionListener(e -> {
            String inputId = JOptionPane.showInputDialog(this, "Enter Reservation ID to cancel:");
            if (inputId == null || inputId.isEmpty()) return;

            try {
                int id = Integer.parseInt(inputId);
                boolean cancelled = new ReservationDAO().cancelReservation(id);
                JOptionPane.showMessageDialog(this, cancelled ? "🗑️ Reservation cancelled." : "❌ Cancellation failed.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid input.");
            }
        });

        back.addActionListener(e -> {
            this.dispose();
            mainFrame.setVisible(true);
        });

        exit.addActionListener(e -> dispose());

        setVisible(true);
    }
}
