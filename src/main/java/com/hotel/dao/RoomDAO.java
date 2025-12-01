package main.java.com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.hotel.config.DBConnection;
import main.java.com.hotel.model.Room;

public class RoomDAO {
    public List<Room> findAll() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms ORDER BY room_number";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Room r = new Room();
                r.setId(rs.getInt("id"));
                r.setRoomNumber(rs.getString("room_number"));
                r.setRoomType(rs.getString("room_type"));
                r.setPricePerDay(rs.getDouble("price_per_day"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int roomId, String status) {
        String sql = "UPDATE rooms SET status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, roomId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createRoom(String roomNumber, String roomType, double price) {
        String sql = "INSERT INTO rooms (room_number, room_type, price_per_day, status) VALUES (?, ?, ?, 'AVAILABLE')";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomNumber);
            ps.setString(2, roomType);
            ps.setDouble(3, price);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}