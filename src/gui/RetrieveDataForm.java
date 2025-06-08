package gui;

import dao.GuestDAO;
import dao.HotelDAO;
import dao.ReservationDAO;
import dao.RoomDAO;
import model.Guest;
import model.Hotel;
import model.Reservation;
import model.Room;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RetrieveDataForm extends JFrame {
    public RetrieveDataForm(MainFrame mainFrame) {
        setTitle("Retrieve Data");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Hotels Tab
        tabbedPane.add("Hotels", createHotelTable());

        // Guests Tab
        tabbedPane.add("Guests", createGuestTable());

        // Rooms Tab
        tabbedPane.add("Rooms", createRoomTable());

        // Reservations Tab
        tabbedPane.add("Reservations", createReservationTable());

        add(tabbedPane, BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            this.dispose();
            mainFrame.setVisible(true);
        });

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(back);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JScrollPane createHotelTable() {
        List<Hotel> hotels = new HotelDAO().getAllHotels();
        String[] cols = {"ID", "Name", "Address", "Amenities", "Phone"};
        Object[][] data = new Object[hotels.size()][cols.length];
        for (int i = 0; i < hotels.size(); i++) {
            Hotel h = hotels.get(i);
            data[i][0] = h.getId();
            data[i][1] = h.getName();
            data[i][2] = h.getAddress();
            data[i][3] = h.getAmenities();
            data[i][4] = h.getPhone();
        }
        return new JScrollPane(new JTable(data, cols));
    }

    private JScrollPane createGuestTable() {
        List<Guest> guests = new GuestDAO().getAllGuests();
        String[] cols = {"ID", "Name", "Email", "Phone"};
        Object[][] data = new Object[guests.size()][cols.length];
        for (int i = 0; i < guests.size(); i++) {
            Guest g = guests.get(i);
            data[i][0] = g.getId();
            data[i][1] = g.getName();
            data[i][2] = g.getEmail();
            data[i][3] = g.getPhone();
        }
        return new JScrollPane(new JTable(data, cols));
    }

    private JScrollPane createRoomTable() {
        List<Room> rooms = new RoomDAO().getAllRooms();
        String[] cols = {"ID", "Hotel ID", "Room No.", "Type", "Price", "Status"};
        Object[][] data = new Object[rooms.size()][cols.length];
        for (int i = 0; i < rooms.size(); i++) {
            Room r = rooms.get(i);
            data[i][0] = r.getId();
            data[i][1] = r.getHotelId();
            data[i][2] = r.getRoomNumber();
            data[i][3] = r.getType();
            data[i][4] = r.getPrice();
            data[i][5] = r.getStatus();
        }
        return new JScrollPane(new JTable(data, cols));
    }

    private JScrollPane createReservationTable() {
        List<Reservation> resList = new ReservationDAO().getAllReservations();
        String[] cols = {"ID", "Guest ID", "Room ID", "Check-In", "Check-Out", "Total Price"};
        Object[][] data = new Object[resList.size()][cols.length];
        for (int i = 0; i < resList.size(); i++) {
            Reservation r = resList.get(i);
            data[i][0] = r.getId();
            data[i][1] = r.getGuestId();
            data[i][2] = r.getRoomId();
            data[i][3] = r.getCheckInDate();
            data[i][4] = r.getCheckOutDate();
            data[i][5] = r.getTotalPrice();
        }
        return new JScrollPane(new JTable(data, cols));
    }
}
