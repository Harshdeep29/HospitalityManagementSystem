package gui;

import dao.HotelDAO;
import model.Hotel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HotelForm extends JFrame {
    private final MainFrame mainFrame;
    public HotelForm(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setTitle("Hotel Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Hotel Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JButton addHotel = new JButton("Add Hotel");
        JButton deleteHotel = new JButton("Delete Hotel");
        JButton editHotel = new JButton("Edit Hotel");
        JButton viewHotel = new JButton("View Hotel");

        JPanel panel = new JPanel(new GridLayout(4,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
        panel.add(addHotel);
        panel.add(deleteHotel);
        panel.add(editHotel);
        panel.add(viewHotel);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel= new JPanel(new FlowLayout());
        JButton back = new JButton("Back");
        JButton exit = new JButton("Exit");
        buttonPanel.add(back);
        buttonPanel.add(exit);
        add(buttonPanel, BorderLayout.SOUTH);

        back.addActionListener(e -> {
            this.dispose();
            this.mainFrame.setVisible(true);
        });
        exit.addActionListener(e -> {
            System.exit(0);
            mainFrame.setVisible(true);
        });

        //Add Hotel
        addHotel.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField addressField = new JTextField();
            JTextField amenitiesField = new JTextField();
            JTextField phoneField = new JTextField();
            Dimension fieldSize = new Dimension(200, 25);
            nameField.setPreferredSize(fieldSize);
            addressField.setPreferredSize(fieldSize);
            amenitiesField.setPreferredSize(fieldSize);
            phoneField.setPreferredSize(fieldSize);

            JPanel inputPanel = new JPanel(new GridLayout(4,2));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Address:"));
            inputPanel.add(addressField);
            inputPanel.add(new JLabel("Amenities:"));
            inputPanel.add(amenitiesField);
            inputPanel.add(new JLabel("Phone:"));
            inputPanel.add(phoneField);

            int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Hotel Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                String amenities = amenitiesField.getText().trim();
                String phone = phoneField.getText().trim();

                if (name.isEmpty() || address.isEmpty() || amenities.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.");
                    return;
                }

                if (!phone.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(this, "Phone must be 10 digits.");
                    return;
                }

                Hotel hotel = new Hotel(0, name, address, amenities, phone);
                HotelDAO dao = new HotelDAO();
                boolean success = dao.addHotel(hotel);
                JOptionPane.showMessageDialog(this, success ? "✅ Hotel added!" : "❌ Failed to add hotel.");
            }
        });

        //View Hotel
        viewHotel.addActionListener(e -> {
           HotelDAO dao = new HotelDAO();
           List<Hotel> hotels = dao.getAllHotels();

           String[] columnNames = {"Hotel ID", "Name", "Address", "Amenities", "Phone"};
           Object[][] data = new Object[hotels.size()][columnNames.length];
           for (int i = 0; i < hotels.size(); i++) {
               data[i][0] = hotels.get(i).getId();
               data[i][1] = hotels.get(i).getName();
               data[i][2] = hotels.get(i).getAddress();
               data[i][3] = hotels.get(i).getAmenities();
               data[i][4] = hotels.get(i).getPhone();
           }

           JTable table = new JTable(data, columnNames);
           JScrollPane scrollPane = new JScrollPane(table);

           JOptionPane.showMessageDialog(this, scrollPane, "All Hotels", JOptionPane.INFORMATION_MESSAGE);
        });

        //Edit Hotel by ID
        editHotel.addActionListener(e -> {
           String inputID = JOptionPane.showInputDialog(this,"Enter Hotel ID to edit:");
           if(inputID == null || inputID.equals("")) {
               JOptionPane.showMessageDialog(this, "Please enter a valid Hotel ID.");
               return;
           }
           try{
               int hotelID = Integer.parseInt(inputID);
               HotelDAO dao = new HotelDAO();
               Hotel hotel = dao.getHotelById(hotelID);
               if(hotel == null) {
                   JOptionPane.showMessageDialog(this, "Hotel does not exist.");
                   return;
               }

               JTextField nameField = new JTextField(hotel.getName());
               JTextField addressField = new JTextField(hotel.getAddress());
               JTextField amenitiesField = new JTextField(hotel.getAmenities());
               JTextField phoneField = new JTextField(hotel.getPhone());

               JPanel inputPanel = new JPanel(new GridLayout(4,2));
               inputPanel.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
               inputPanel.add(new JLabel("Name:"));
               inputPanel.add(nameField);
               inputPanel.add(new JLabel("Address:"));
               inputPanel.add(addressField);
               inputPanel.add(new JLabel("Amenities:"));
               inputPanel.add(amenitiesField);
               inputPanel.add(new JLabel("Phone:"));
               inputPanel.add(phoneField);

               int result = JOptionPane.showConfirmDialog(this, inputPanel, "Edit Hotel Details", JOptionPane.OK_CANCEL_OPTION);
               if (result == JOptionPane.OK_OPTION) {
                   hotel.setName(nameField.getText());
                   hotel.setAddress(addressField.getText());
                   hotel.setAmenities(amenitiesField.getText());
                   hotel.setPhone(phoneField.getText());

                   boolean success = dao.updateHotel(hotel);
                   JOptionPane.showMessageDialog(this, success ? "<UNK> Hotel updated!" : "<UNK> Failed to update hotel.");
               }
           }catch(NumberFormatException nfe){
               JOptionPane.showMessageDialog(this, "Please enter a valid Hotel ID.");
           }
        });

        // Delete Hotel by ID
        deleteHotel.addActionListener(e -> {
            String inputId = JOptionPane.showInputDialog(this, "Enter Hotel ID to Delete:");
            if (inputId == null || inputId.isEmpty()) {
                System.out.println("Please enter a valid Hotel ID.");
                return;
            }

            try {
                int id = Integer.parseInt(inputId);
                HotelDAO dao = new HotelDAO();
                boolean deleted = dao.deleteHotel(id);
                JOptionPane.showMessageDialog(this, deleted ? "🗑️ Hotel deleted." : "❌ Hotel not found or deletion failed.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID entered.");
            }
        });

        setVisible(true);

    }
}
