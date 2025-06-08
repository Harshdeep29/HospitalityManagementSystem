package dao;

import db.DatabaseConnector;
import model.Guest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {
    //Add Guest
    public boolean addGuest(Guest guest) {
        String sql = "insert into guests (name, email,phone) values (?,?,?)";
        try(Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, guest.getName());
            stmt.setString(2, guest.getEmail());
            stmt.setString(3, guest.getPhone());

            int rowsAdded=stmt.executeUpdate();
            return rowsAdded>0;
        }catch(SQLException e){
            System.out.println("Error adding guest: "+ e.getMessage());
            return false;
        }
    }
    //Get all Guests
    public List<Guest> getAllGuests() {
        String sql = "select * from guests";
        List<Guest> guests = new ArrayList<>();
        try(Connection conn = DatabaseConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                Guest guest = new Guest(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                guests.add(guest);
            }
        }catch (SQLException e){
            System.out.println("Error fetching guests: "+ e.getMessage());
        }
        return guests;
    }
    //Get Guest by ID
    public Guest getGuestById(int id) {
        String sql = "select * from guests where id=?";
        try(Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new Guest(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching guest: "+ e.getMessage());
        }
        return null;
    }
    //Update Guest
    public boolean updateGuest(Guest guest) {
        String sql = "update guests set name=?,email=?,phone=? where id=?";
        try(Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, guest.getName());
            stmt.setString(2, guest.getEmail());
            stmt.setString(3, guest.getPhone());
            stmt.setInt(4, guest.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated>0;
        }catch (SQLException e){
            System.out.println("Error updating guest: "+ e.getMessage());
            return false;
        }
    }
    //Delete Guest
    public boolean deleteGuest(int id) {
        String sql = "delete from guests where id=?";
        try(Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated>0;
        } catch (SQLException e) {
            System.out.println("Error deleting guest: "+ e.getMessage());
            return false;
        }
    }

    public List<Guest> searchGuestsByName(String nameQuery) {
        List<Guest> guests = new ArrayList<>();
        String sql = "SELECT * FROM guests WHERE name LIKE ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nameQuery + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                guests.add(new Guest(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error searching guests: " + e.getMessage());
        }
        return guests;
    }

}
