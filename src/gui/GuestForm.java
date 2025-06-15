package gui;

import dao.GuestDAO;
import model.Guest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GuestForm extends JFrame {
    private final JFrame parentFrame;
    public GuestForm(JFrame parentFrame) {

        this.parentFrame = parentFrame;

        setTitle("Guest Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Guest Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JButton addGuest = new JButton("Add Guest");
        JButton deleteGuest = new JButton("Delete Guest");
        JButton editGuest = new JButton("Edit Guest");
        JButton viewGuest = new JButton("View Guest");
        JButton searchGuest = new JButton("Search Guest");

        JPanel panel = new JPanel(new GridLayout(5,1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        panel.add(addGuest);
        panel.add(deleteGuest);
        panel.add(editGuest);
        panel.add(viewGuest);
        panel.add(searchGuest);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton back = new JButton("Back");
        JButton exit = new JButton("Exit");
        buttonPanel.add(back);
        buttonPanel.add(exit);
        add(buttonPanel, BorderLayout.SOUTH);

        addGuest.addActionListener(e -> {
            JTextField guestName = new JTextField();
            JTextField guestEmail = new JTextField();
            JTextField guestPhone = new JTextField();
            Dimension fieldSize = new Dimension(200, 25);
            guestName.setPreferredSize(fieldSize);
            guestEmail.setPreferredSize(fieldSize);
            guestPhone.setPreferredSize(fieldSize);

            JPanel inputPanel = new JPanel(new GridLayout(3,1));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
            inputPanel.add(new JLabel("Guest Name:"));
            inputPanel.add(guestName);
            inputPanel.add(new JLabel("Guest Email:"));
            inputPanel.add(guestEmail);
            inputPanel.add(new JLabel("Guest Phone:"));
            inputPanel.add(guestPhone);

            int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Guest Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String name = guestName.getText().trim();
                String email = guestEmail.getText().trim();
                String phone = guestPhone.getText().trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.");
                    return;
                }

                if (!email.contains("@") || !email.contains(".")) {
                    JOptionPane.showMessageDialog(this, "Invalid email address.");
                    return;
                }

                if (!phone.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(this, "Phone must be 10 digits.");
                    return;
                }

                Guest guest = new Guest(0, name, email, phone);

                GuestDAO guestDAO = new GuestDAO();
                boolean success = guestDAO.addGuest(guest);
                JOptionPane.showMessageDialog(this, success ? "✅ Guest added!" : "❌ Failed to add guest.");
            }
        });

        viewGuest.addActionListener(e -> {
            GuestDAO guestDAO = new GuestDAO();
            List<Guest> guests = guestDAO.getAllGuests();

            String[] colNames = {"Guest ID", "Guest Name", "Guest Email", "Guest Phone"};
            Object[][] data = new Object[guests.size()][colNames.length];
            for (int i = 0; i < guests.size(); i++) {
                data[i][0] = guests.get(i).getId();
                data[i][1] = guests.get(i).getName();
                data[i][2] = guests.get(i).getEmail();
                data[i][3] = guests.get(i).getPhone();
            }

            JTable table = new JTable(data, colNames);
            JScrollPane scrollPane = new JScrollPane(table);

            JOptionPane.showMessageDialog(this, scrollPane,"All Guest Details", JOptionPane.INFORMATION_MESSAGE);
        });

        editGuest.addActionListener(e -> {
            String inputID = JOptionPane.showInputDialog(this, "Enter Guest ID to edit:");
            if(inputID==null || inputID.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Guest ID");
                return;
            }
            try{
                int guestID = Integer.parseInt(inputID);
                GuestDAO guestDAO = new GuestDAO();
                Guest guest = guestDAO.getGuestById(guestID);
                if(guest==null) {
                    JOptionPane.showMessageDialog(this, "Guest Not Found");
                    return;
                }

                JTextField guestName = new JTextField(guest.getName());
                JTextField guestEmail = new JTextField(guest.getEmail());
                JTextField guestPhone = new JTextField(guest.getPhone());

                JPanel inputPanel = new JPanel(new GridLayout(3,2));
                inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
                inputPanel.add(new JLabel("Guest Name:"));
                inputPanel.add(guestName);
                inputPanel.add(new JLabel("Guest Email:"));
                inputPanel.add(guestEmail);
                inputPanel.add(new JLabel("Guest Phone:"));
                inputPanel.add(guestPhone);

                int result = JOptionPane.showConfirmDialog(this,inputPanel, "Edit Guest Details", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    guest.setName(guestName.getText());
                    guest.setEmail(guestEmail.getText());
                    guest.setPhone(guestPhone.getText());

                    boolean success = guestDAO.updateGuest(guest);
                    JOptionPane.showMessageDialog(this, success ? "<UNK> Guest updated!" : "<UNK> Failed to update Guest.");
                }
            }catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(this, "Please enter a valid Guest ID");
            }
        });

        deleteGuest.addActionListener(e -> {
            String inputID = JOptionPane.showInputDialog(this, "Enter Guest ID to delete:");
            if(inputID==null || inputID.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Guest ID");
                return;
            }

            try{
                int guestID = Integer.parseInt(inputID);
                GuestDAO guestDAO = new GuestDAO();
                boolean success = guestDAO.deleteGuest(guestID);
                JOptionPane.showMessageDialog(this,success ? "🗑️ Guest deleted." : "❌ Guest not found or deletion failed.");
            }catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(this, "Please enter a valid Guest ID");
            }
        });

        searchGuest.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter guest name to search:");
            if (input == null || input.trim().isEmpty()) return;

            GuestDAO guestDAO = new GuestDAO();
            List<Guest> guests = guestDAO.searchGuestsByName(input.trim());

            if (guests.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No guests found.");
                return;
            }

            String[] cols = {"ID", "Name", "Email", "Phone"};
            Object[][] data = new Object[guests.size()][4];
            for (int i = 0; i < guests.size(); i++) {
                Guest g = guests.get(i);
                data[i][0] = g.getId();
                data[i][1] = g.getName();
                data[i][2] = g.getEmail();
                data[i][3] = g.getPhone();
            }

            JTable table = new JTable(data, cols);
            JScrollPane scrollPane = new JScrollPane(table);
            JOptionPane.showMessageDialog(this, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
        });

        back.addActionListener(e -> {
            this.dispose();
            if (parentFrame != null) parentFrame.setVisible(true);
        });

        exit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Exit the application?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        setVisible(true);

    }

    public GuestForm() {
        this(null);
    }
}
