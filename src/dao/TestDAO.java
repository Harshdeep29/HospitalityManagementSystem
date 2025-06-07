package util;

import dao.RoomDAO;
import model.Room;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        RoomDAO dao = new RoomDAO();

        // 1. Add a room (hotel ID must exist!)
        Room room = new Room(0, 1, 101, "Deluxe", 3500.0, "Available");
        if (dao.addRoom(room)) {
            System.out.println("✅ Room added successfully!");
        } else {
            System.out.println("❌ Failed to add room.");
        }

        // 2. View all rooms
        List<Room> rooms = dao.getAllRooms();
        System.out.println("📋 All Rooms:");
        for (Room r : rooms) {
            System.out.println(r);
        }

        // 3. Get room by ID (e.g., ID = 1)
        Room fetched = dao.getRoomById(1);
        if (fetched != null) {
            System.out.println("🔍 Room with ID 1: " + fetched);
        } else {
            System.out.println("❌ Room with ID 1 not found.");
        }

        // 4. Update a room (e.g., ID = 1)
        Room updatedRoom = new Room(1, 1, 101, "Suite", 4200.0, "Booked");
        if (dao.updateRoom(updatedRoom)) {
            System.out.println("✅ Room updated.");
        } else {
            System.out.println("❌ Failed to update room.");
        }

        // 5. Delete room by ID (e.g., ID = 1)
        if (dao.deleteRoom(1)) {
            System.out.println("🗑️ Room deleted.");
        } else {
            System.out.println("❌ Room not deleted.");
        }
    }
}
