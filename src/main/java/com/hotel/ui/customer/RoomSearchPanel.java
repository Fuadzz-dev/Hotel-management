package main.java.com.hotel.ui.customer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.java.com.hotel.dao.RoomDAO;
import main.java.com.hotel.model.Room;

public class RoomSearchPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbRoomType;
    private JComboBox<String> cbStatus;
    private JTextField tfMaxPrice;
    private RoomDAO roomDAO;

    public RoomSearchPanel() {
        roomDAO = new RoomDAO();
        setLayout(new BorderLayout());
        init();
        loadAllRooms();
    }

    private void init() {
        // Panel Pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        searchPanel.add(new JLabel("Room Type:"));
        cbRoomType = new JComboBox<>(new String[]{"ALL", "STANDARD", "DELUXE", "SUITE"});
        searchPanel.add(cbRoomType);
        
        searchPanel.add(new JLabel("Status:"));
        cbStatus = new JComboBox<>(new String[]{"ALL", "AVAILABLE", "OCCUPIED", "MAINTENANCE"});
        searchPanel.add(cbStatus);
        
        searchPanel.add(new JLabel("Max Price:"));
        tfMaxPrice = new JTextField(10);
        searchPanel.add(tfMaxPrice);
        
        JButton btnSearch = new JButton("Search");
        JButton btnReset = new JButton("Reset");
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Table
        String[] cols = {"ID", "Room Number", "Type", "Price/Day", "Status"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { 
                return false; 
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Action Listeners
        btnSearch.addActionListener(e -> searchRooms());
        btnReset.addActionListener(e -> {
            cbRoomType.setSelectedIndex(0);
            cbStatus.setSelectedIndex(0);
            tfMaxPrice.setText("");
            loadAllRooms();
        });
    }

    private void loadAllRooms() {
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

    private void searchRooms() {
        model.setRowCount(0);
        List<Room> rooms = roomDAO.findAll();
        
        String selectedType = (String) cbRoomType.getSelectedItem();
        String selectedStatus = (String) cbStatus.getSelectedItem();
        String maxPriceText = tfMaxPrice.getText().trim();
        
        double maxPrice = maxPriceText.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceText);
        
        for (Room r : rooms) {
            boolean matchType = "ALL".equals(selectedType) || r.getRoomType().equals(selectedType);
            boolean matchStatus = "ALL".equals(selectedStatus) || r.getStatus().equals(selectedStatus);
            boolean matchPrice = r.getPricePerDay() <= maxPrice;
            
            if (matchType && matchStatus && matchPrice) {
                model.addRow(new Object[]{
                    r.getId(), 
                    r.getRoomNumber(), 
                    r.getRoomType(), 
                    String.format("Rp %.0f", r.getPricePerDay()), 
                    r.getStatus()
                });
            }
        }
    }
}