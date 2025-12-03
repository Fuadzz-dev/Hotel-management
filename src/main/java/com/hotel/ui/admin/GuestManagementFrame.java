package main.java.com.hotel.ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.com.hotel.config.DBConnection;

public class GuestManagementFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public GuestManagementFrame() {
        setTitle("Guest Management");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        loadData();
    }

    private void init() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(52, 73, 94));
        JLabel lblTitle = new JLabel("Guest Management");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(lblTitle);

        String[] columns = {"ID", "Username", "Full Name", "Role", "Total Reservations", "Total Spent", "Created At"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnRefresh = new JButton("Refresh");
        JButton btnViewDetail = new JButton("View Guest Detail");
        JButton btnDelete = new JButton("Delete Guest");
        
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnViewDetail.setBackground(new Color(46, 204, 113));
        btnDelete.setBackground(new Color(231, 76, 60));
        
        for (JButton btn : new JButton[]{btnRefresh, btnViewDetail, btnDelete}) {
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            buttonPanel.add(btn);
        }

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
        btnViewDetail.addActionListener(e -> viewGuestDetail());
        btnDelete.addActionListener(e -> deleteGuest());
    }

    private void loadData() {
        model.setRowCount(0);
        String sql = "SELECT u.id, u.username, u.full_name, u.role, u.created_at, " +
                     "COUNT(r.id) as total_reservations, " +
                     "COALESCE(SUM(CASE WHEN r.status = 'CHECKED_OUT' THEN r.total_price ELSE 0 END), 0) as total_spent " +
                     "FROM users u " +
                     "LEFT JOIN reservations r ON u.id = r.user_id " +
                     "WHERE u.role = 'CUSTOMER' " +
                     "GROUP BY u.id " +
                     "ORDER BY u.created_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("full_name"),
                    rs.getString("role"),
                    rs.getInt("total_reservations"),
                    String.format("Rp %.0f", rs.getDouble("total_spent")),
                    rs.getTimestamp("created_at").toString()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading guest data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewGuestDetail() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a guest!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int guestId = (int) model.getValueAt(selectedRow, 0);
        String guestName = (String) model.getValueAt(selectedRow, 2);
        
        StringBuilder detail = new StringBuilder();
        detail.append("Guest Details:\n\n");
        detail.append("Name: ").append(guestName).append("\n");
        detail.append("Username: ").append(model.getValueAt(selectedRow, 1)).append("\n");
        detail.append("Total Reservations: ").append(model.getValueAt(selectedRow, 4)).append("\n");
        detail.append("Total Spent: ").append(model.getValueAt(selectedRow, 5)).append("\n\n");
        
        // Get recent reservations
        String sql = "SELECT r.id, rm.room_number, r.check_in, r.check_out, r.status, r.total_price " +
                     "FROM reservations r " +
                     "JOIN rooms rm ON r.room_id = rm.id " +
                     "WHERE r.user_id = ? " +
                     "ORDER BY r.created_at DESC LIMIT 5";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, guestId);
            ResultSet rs = ps.executeQuery();
            
            detail.append("Recent Reservations:\n");
            while (rs.next()) {
                detail.append(String.format("- Room %s | %s to %s | %s | Rp %.0f\n",
                    rs.getString("room_number"),
                    rs.getDate("check_in"),
                    rs.getDate("check_out"),
                    rs.getString("status"),
                    rs.getDouble("total_price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JOptionPane.showMessageDialog(this, detail.toString(), "Guest Detail", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteGuest() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a guest to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int guestId = (int) model.getValueAt(selectedRow, 0);
        String guestName = (String) model.getValueAt(selectedRow, 2);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete guest: " + guestName + "?\nThis will also delete all their reservations.", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM users WHERE id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, guestId);
                int deleted = ps.executeUpdate();
                
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(this, "Guest deleted successfully!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete guest!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}