package main.java.com.hotel.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_db?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // sesuaikan jika ada password
    private static Connection conn = null;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        return conn;
    }
}
