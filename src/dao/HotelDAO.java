package dao;

import model.Hotel;
import db.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {
    //Add Hotel
    public boolean addHotel(Hotel hotel) {
        String sql = "insert into hotels (name, address, amenities, phone) values(?,?,?,?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getAddress());
            stmt.setString(3, hotel.getAmenities());
            stmt.setString(4, hotel.getPhone());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error adding hotel: " + e.getMessage());
            return false;
        }
    }
    //Get All Hotels
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "select * from hotels";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Hotel hotel = new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("amenities"),
                        rs.getString("phone")
                );
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching hotels: " + e.getMessage());
        }
        return hotels;
    }
    //Get Hotel by ID
    public Hotel getHotelById(int id) {
        String sql = "select * from hotels where id = ?";
        try(Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("amenities"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching hotel: " + e.getMessage());
        }
        return null;
    }
    //Update Hotel
    public boolean updateHotel(Hotel hotel) {
        String sql = "Update hotels SET name = ?, address = ?, amenities = ? where id = ?";
        try(Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getAddress());
            stmt.setString(3, hotel.getAmenities());
            stmt.setInt(4, hotel.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }catch(SQLException e){
            System.out.println("Error updating hotel: " + e.getMessage());
            return false;
        }
    }
    //Delete Hotel
    public boolean deleteHotel(int id) {
        String sql = "delete from hotels where id = ?";
        try(Connection conn = DatabaseConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }catch(SQLException e){
            System.out.println("Error deleting hotel: " + e.getMessage());
            return false;
        }
    }
}