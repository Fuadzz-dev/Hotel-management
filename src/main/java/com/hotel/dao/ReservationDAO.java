package main.java.com.hotel.dao;

import com.hotel.config.DBConnection;
import com.hotel.model.Reservation;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    
    public boolean createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (user_id, room_id, check_in, check_out, total_price, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservation.getUserId());
            ps.setInt(2, reservation.getRoomId());
            ps.setDate(3, Date.valueOf(reservation.getCheckIn()));
            ps.setDate(4, Date.valueOf(reservation.getCheckOut()));
            ps.setDouble(5, reservation.getTotalPrice());
            ps.setString(6, reservation.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Reservation> findByUserId(int userId) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT r.*, rm.room_number FROM reservations r " +
                     "JOIN rooms rm ON r.room_id = rm.id " +
                     "WHERE r.user_id = ? ORDER BY r.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation res = new Reservation();
                res.setId(rs.getInt("id"));
                res.setUserId(rs.getInt("user_id"));
                res.setRoomId(rs.getInt("room_id"));
                res.setCheckIn(rs.getDate("check_in").toLocalDate());
                res.setCheckOut(rs.getDate("check_out").toLocalDate());
                res.setTotalPrice(rs.getDouble("total_price"));
                res.setStatus(rs.getString("status"));
                res.setRoomNumber(rs.getString("room_number"));
                list.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> findAll() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT r.*, rm.room_number, u.full_name FROM reservations r " +
                     "JOIN rooms rm ON r.room_id = rm.id " +
                     "JOIN users u ON r.user_id = u.id " +
                     "ORDER BY r.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Reservation res = new Reservation();
                res.setId(rs.getInt("id"));
                res.setUserId(rs.getInt("user_id"));
                res.setRoomId(rs.getInt("room_id"));
                res.setCheckIn(rs.getDate("check_in").toLocalDate());
                res.setCheckOut(rs.getDate("check_out").toLocalDate());
                res.setTotalPrice(rs.getDouble("total_price"));
                res.setStatus(rs.getString("status"));
                res.setRoomNumber(rs.getString("room_number"));
                res.setUserName(rs.getString("full_name"));
                list.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int reservationId, String status) {
        String sql = "UPDATE reservations SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, reservationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getTotalRevenue() {
        String sql = "SELECT SUM(total_price) as total FROM reservations WHERE status IN ('CHECKED_OUT', 'CHECKED_IN')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}