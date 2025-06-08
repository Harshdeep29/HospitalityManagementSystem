package gui;

import dao.RoomDAO;
import model.Room;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoomForm extends JFrame {
    private final MainFrame mainFrame;

    public RoomForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setTitle("Room Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Room Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JButton addRoom = new JButton("Add Room");
        JButton deleteRoom = new JButton("Delete Room");
        JButton editRoom = new JButton("Edit Room");
        JButton viewRoom = new JButton("View Room");

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        panel.add(addRoom);
        panel.add(deleteRoom);
        panel.add(editRoom);
        panel.add(viewRoom);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton back = new JButton("Back");
        JButton exit = new JButton("Exit");
        buttonPanel.add(back);
        buttonPanel.add(exit);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Room
        addRoom.addActionListener(e -> {
            JTextField hotelIdField = new JTextField();
            JTextField roomNumberField = new JTextField();
            JTextField typeField = new JTextField();
            JTextField priceField = new JTextField();
            JTextField statusField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(5, 2));
            inputPanel.add(new JLabel("Hotel ID:"));
            inputPanel.add(hotelIdField);
            inputPanel.add(new JLabel("Room Number:"));
            inputPanel.add(roomNumberField);
            inputPanel.add(new JLabel("Type:"));
            inputPanel.add(typeField);
            inputPanel.add(new JLabel("Price:"));
            inputPanel.add(priceField);
            inputPanel.add(new JLabel("Status:"));
            inputPanel.add(statusField);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter Room Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int hotelId = Integer.parseInt(hotelIdField.getText().trim());
                    int roomNumber = Integer.parseInt(roomNumberField.getText().trim());
                    String type = typeField.getText().trim();
                    double price = Double.parseDouble(priceField.getText().trim());
                    String status = statusField.getText().trim();

                    if (type.isEmpty() || status.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Room type and status are required.");
                        return;
                    }

                    if (price <= 0) {
                        JOptionPane.showMessageDialog(this, "Price must be greater than 0.");
                        return;
                    }

                    Room room = new Room(0, hotelId, roomNumber, type, price, status);
                    boolean success = new RoomDAO().addRoom(room);
                    JOptionPane.showMessageDialog(this, success ? "✅ Room added!" : "❌ Failed to add room.");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number format. Please enter numeric values.");
                }
            }
        });

        // View Room
        viewRoom.addActionListener(e -> {
            List<Room> rooms = new RoomDAO().getAllRooms();
            String[] colNames = {"ID", "Hotel ID", "Room No.", "Type", "Price", "Status"};
            Object[][] data = new Object[rooms.size()][6];
            for (int i = 0; i < rooms.size(); i++) {
                Room r = rooms.get(i);
                data[i][0] = r.getId();
                data[i][1] = r.getHotelId();
                data[i][2] = r.getRoomNumber();
                data[i][3] = r.getType();
                data[i][4] = r.getPrice();
                data[i][5] = r.getStatus();
            }
            JTable table = new JTable(data, colNames);
            JOptionPane.showMessageDialog(this, new JScrollPane(table), "All Rooms", JOptionPane.INFORMATION_MESSAGE);
        });

        // Edit Room
        editRoom.addActionListener(e -> {
            String inputId = JOptionPane.showInputDialog(this, "Enter Room ID to edit:");
            if (inputId == null || inputId.isEmpty()) return;
            try {
                int id = Integer.parseInt(inputId);
                Room room = new RoomDAO().getRoomById(id);
                if (room == null) {
                    JOptionPane.showMessageDialog(this, "Room not found.");
                    return;
                }

                JTextField hotelIdField = new JTextField(String.valueOf(room.getHotelId()));
                JTextField roomNumberField = new JTextField(String.valueOf(room.getRoomNumber()));
                JTextField typeField = new JTextField(room.getType());
                JTextField priceField = new JTextField(String.valueOf(room.getPrice()));
                JTextField statusField = new JTextField(room.getStatus());

                JPanel inputPanel = new JPanel(new GridLayout(5, 2));
                inputPanel.add(new JLabel("Hotel ID:"));
                inputPanel.add(hotelIdField);
                inputPanel.add(new JLabel("Room Number:"));
                inputPanel.add(roomNumberField);
                inputPanel.add(new JLabel("Type:"));
                inputPanel.add(typeField);
                inputPanel.add(new JLabel("Price:"));
                inputPanel.add(priceField);
                inputPanel.add(new JLabel("Status:"));
                inputPanel.add(statusField);

                int result = JOptionPane.showConfirmDialog(this, inputPanel, "Edit Room Details", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    room.setHotelId(Integer.parseInt(hotelIdField.getText()));
                    room.setRoomNumber(Integer.parseInt(roomNumberField.getText()));
                    room.setType(typeField.getText());
                    room.setPrice(Double.parseDouble(priceField.getText()));
                    room.setStatus(statusField.getText());

                    boolean updated = new RoomDAO().updateRoom(room);
                    JOptionPane.showMessageDialog(this, updated ? "✅ Room updated!" : "❌ Update failed.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid input!");
            }
        });

        // Delete Room
        deleteRoom.addActionListener(e -> {
            String inputId = JOptionPane.showInputDialog(this, "Enter Room ID to delete:");
            if (inputId == null || inputId.isEmpty()) return;
            try {
                int id = Integer.parseInt(inputId);
                boolean deleted = new RoomDAO().deleteRoom(id);
                JOptionPane.showMessageDialog(this, deleted ? "🗑️ Room deleted." : "❌ Deletion failed.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid Room ID!");
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
