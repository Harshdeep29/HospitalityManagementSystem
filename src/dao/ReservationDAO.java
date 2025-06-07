package dao;

import db.DatabaseConnector;
import model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // Add Reservation
    public boolean addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (guest_id, room_id, check_in_date, check_out_date, total_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getGuestId());
            stmt.setInt(2, reservation.getRoomId());
            stmt.setString(3, reservation.getCheckInDate());
            stmt.setString(4, reservation.getCheckOutDate());
            stmt.setDouble(5, reservation.getTotalPrice());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding reservation: " + e.getMessage());
            return false;
        }
    }

    // Get All Reservations
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reservation res = new Reservation(
                        rs.getInt("id"),
                        rs.getInt("guest_id"),
                        rs.getInt("room_id"),
                        rs.getString("check_in_date"),
                        rs.getString("check_out_date"),
                        rs.getDouble("total_price")
                );
                reservations.add(res);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching reservations: " + e.getMessage());
        }
        return reservations;
    }

    // Get Reservation by ID
    public Reservation getReservationById(int id) {
        String sql = "SELECT * FROM reservations WHERE id=?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Reservation(
                        rs.getInt("id"),
                        rs.getInt("guest_id"),
                        rs.getInt("room_id"),
                        rs.getString("check_in_date"),
                        rs.getString("check_out_date"),
                        rs.getDouble("total_price")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching reservation: " + e.getMessage());
        }
        return null;
    }

    // Cancel Reservation
    public boolean cancelReservation(int id) {
        String sql = "DELETE FROM reservations WHERE id=?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error cancelling reservation: " + e.getMessage());
            return false;
        }
    }

    // Check Room Availability
    public boolean isRoomAvailable(int roomId, String checkIn, String checkOut) {
        String sql = """
            SELECT COUNT(*) FROM reservations 
            WHERE room_id = ? AND 
            (check_in_date < ? AND check_out_date > ?)
            """;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);
            stmt.setString(2, checkOut);
            stmt.setString(3, checkIn);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // available if no overlapping reservations
            }

        } catch (SQLException e) {
            System.out.println("Error checking room availability: " + e.getMessage());
        }
        return false;
    }
}
