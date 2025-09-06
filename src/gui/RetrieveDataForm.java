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
import java.util.function.Function;

public class RetrieveDataForm extends JFrame {
    public RetrieveDataForm(AdminFrame adminFrame) {
        setTitle("Retrieve Data");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Refactored to use a single generic method
        // instead of four separate, repetitive methods.
        tabbedPane.add("Hotels", createTablePanel(
                new HotelDAO().getAllHotels(),
                new String[]{"ID", "Name", "Address", "Amenities", "Phone"},
                hotel -> new Object[]{hotel.getId(), hotel.getName(), hotel.getAddress(), hotel.getAmenities(), hotel.getPhone()}));

        tabbedPane.add("Guests", createTablePanel(
                new GuestDAO().getAllGuests(),
                new String[]{"ID", "Name", "Email", "Phone"},
                guest -> new Object[]{guest.getId(), guest.getName(), guest.getEmail(), guest.getPhone()}));

        tabbedPane.add("Rooms", createTablePanel(
                new RoomDAO().getAllRooms(),
                new String[]{"ID", "Hotel ID", "Room No.", "Type", "Price", "Status"},
                room -> new Object[]{room.getId(), room.getHotelId(), room.getRoomNumber(), room.getType(), room.getPrice(), room.getStatus()}));

        tabbedPane.add("Reservations", createTablePanel(
                new ReservationDAO().getAllReservations(),
                new String[]{"ID", "Guest ID", "Room ID", "Check-In", "Check-Out", "Total Price"},
                reservation -> new Object[]{reservation.getId(), reservation.getGuestId(), reservation.getRoomId(), reservation.getCheckInDate(), reservation.getCheckOutDate(), reservation.getTotalPrice()}));

        add(tabbedPane, BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            this.dispose();
            adminFrame.setVisible(true);
        });

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(back);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private <T> JScrollPane createTablePanel(List<T> dataList, String[] columnNames, Function<T, Object[]> dataExtractor) {
        Object[][] data = new Object[dataList.size()][columnNames.length];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataExtractor.apply(dataList.get(i));
        }

        JTable table = new JTable(data, columnNames);
        return new JScrollPane(table);
    }
}
