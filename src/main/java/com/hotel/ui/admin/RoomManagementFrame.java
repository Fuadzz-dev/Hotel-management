package main.java.com.hotel.ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.com.hotel.dao.RoomDAO;
import main.java.com.hotel.model.Room;

public class RoomManagementFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private RoomDAO roomDAO;

    public RoomManagementFrame() {
        roomDAO = new RoomDAO();
        setTitle("Room Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        loadData();
    }

    private void init() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(52, 73, 94));
        JLabel lblTitle = new JLabel("Room Management");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(lblTitle);

        String[] columns = {"ID", "Room Number", "Type", "Price/Day", "Status"};
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
        JButton btnSetAvailable = new JButton("Set Available");
        JButton btnSetOccupied = new JButton("Set Occupied");
        JButton btnSetMaintenance = new JButton("Set Maintenance");
        JButton btnRefresh = new JButton("Refresh");

        btnSetAvailable.setBackground(new Color(46, 204, 113));
        btnSetOccupied.setBackground(new Color(231, 76, 60));
        btnSetMaintenance.setBackground(new Color(241, 196, 15));
        btnRefresh.setBackground(new Color(52, 152, 219));

        for (JButton btn : new JButton[]{btnSetAvailable, btnSetOccupied, btnSetMaintenance, btnRefresh}) {
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
        }

        buttonPanel.add(btnSetAvailable);
        buttonPanel.add(btnSetOccupied);
        buttonPanel.add(btnSetMaintenance);
        buttonPanel.add(btnRefresh);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSetAvailable.addActionListener(e -> updateRoomStatus("AVAILABLE"));
        btnSetOccupied.addActionListener(e -> updateRoomStatus("OCCUPIED"));
        btnSetMaintenance.addActionListener(e -> updateRoomStatus("MAINTENANCE"));
        btnRefresh.addActionListener(e -> loadData());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Room> rooms = roomDAO.findAll();
        for (Room r : rooms) {
            model.addRow(new Object[]{
                r.getId(),
                r.getRoomNumber(),
                r.getRoomType(),
                String.format("Rp %.0f", r.getPricePerDay()),
                r.getStatus()
            });
        }
    }

    private void updateRoomStatus(String status) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roomId = (int) model.getValueAt(selectedRow, 0);
        if (roomDAO.updateStatus(roomId, status)) {
            JOptionPane.showMessageDialog(this, "Room status updated to " + status);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update room status", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}