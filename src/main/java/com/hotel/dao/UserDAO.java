package main.java.com.hotel.dao;

import com.hotel.config.DBConnection;
import com.hotel.model.User;
import java.sql.*;

public class UserDAO {
    public User login(String username, String password) {
        String sql = "SELECT id, username, full_name, role FROM users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password); // in prod: compare hashed password!
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"),
                                rs.getString("full_name"), rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
