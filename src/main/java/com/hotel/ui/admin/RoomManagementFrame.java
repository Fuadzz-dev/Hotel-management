package main.java.com.hotel.ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
        setSize(900, 550);
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
                return column == 4;  // Hanya kolom status yang bisa di-edit
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);

        // ComboBox untuk kolom Status
        String[] statusOptions = {"AVAILABLE", "OCCUPIED", "MAINTENANCE"};
        JComboBox<String> comboStatus = new JComboBox<>(statusOptions);
        table.getColumnModel().getColumn(4).setCellEditor(new javax.swing.DefaultCellEditor(comboStatus));

        JScrollPane scrollPane = new JScrollPane(table);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnRefresh = new JButton("Refresh");
        JButton btnAddRoom = new JButton("Add Room");

        btnRefresh.setBackground(new Color(52, 152, 219));
        btnAddRoom.setBackground(new Color(46, 204, 113));

        for (JButton btn : new JButton[]{btnRefresh, btnAddRoom}) {
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            buttonPanel.add(btn);
        }

        // Listener tombol
        btnRefresh.addActionListener(e -> loadData());
        btnAddRoom.addActionListener(e -> showAddRoomDialog());

        // Listener auto-update status
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 4) { // Status
                int roomId = (int) model.getValueAt(row, 0);
                String newStatus = (String) model.getValueAt(row, 4);

                boolean success = roomDAO.updateStatus(roomId, newStatus);
                if (!success) {
                    JOptionPane.showMessageDialog(this, "Failed to update room status!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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

    private void showAddRoomDialog() {
        String roomNumber = JOptionPane.showInputDialog(this, "Enter Room Number:");
        if (roomNumber == null || roomNumber.trim().isEmpty()) return;

        String[] types = {"STANDARD", "DELUXE", "SUITE"};
        String roomType = (String) JOptionPane.showInputDialog(
                this, "Select Room Type:", "Room Type",
                JOptionPane.QUESTION_MESSAGE, null, types, types[0]
        );

        String priceInput = JOptionPane.showInputDialog(this, "Enter Price/Day:");
        if (priceInput == null || priceInput.trim().isEmpty()) return;

        double price;
        try {
            price = Double.parseDouble(priceInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean created = roomDAO.createRoom(roomNumber, roomType, price);
        if (created) {
            JOptionPane.showMessageDialog(this, "Room added successfully!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add room!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
