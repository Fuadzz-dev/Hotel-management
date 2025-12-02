package main.java.com.hotel.ui.customer;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.java.com.hotel.dao.RoomDAO;
import main.java.com.hotel.model.Room;
import main.java.com.hotel.ui.components.LuxuryUIComponents;

public class RoomDetailsPanel extends JScrollPane {
    private JTable table;
    private DefaultTableModel model;
    
    public RoomDetailsPanel() {
        String[] cols = {"ID", "Room Number", "Room Type", "Price/Day", "Status"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { 
                return false; 
            }
        };
        
        table = new JTable(model);
        LuxuryUIComponents.styleLuxuryTable(table);
        
        setViewportView(table);
        setBorder(BorderFactory.createLineBorder(LuxuryUIComponents.GOLD, 2));
        getViewport().setBackground(LuxuryUIComponents.CREAM);
        
        loadData();
    }
    
    private void loadData() {
        RoomDAO dao = new RoomDAO();
        List<Room> rooms = dao.findAll();
        model.setRowCount(0);
        
        for (Room r : rooms) {
            // Format harga dengan Rupiah
            String price = String.format("Rp %,.0f", r.getPricePerDay());
            
            // Format status dengan emoji
            String status = r.getStatus();
            String statusDisplay = status;
            
            switch(status) {
                case "AVAILABLE":
                    statusDisplay = "✅ " + status;
                    break;
                case "OCCUPIED":
                    statusDisplay = "🔒 " + status;
                    break;
                case "MAINTENANCE":
                    statusDisplay = "🔧 " + status;
                    break;
            }
            
            model.addRow(new Object[]{
                r.getId(), 
                r.getRoomNumber(), 
                r.getRoomType(), 
                price, 
                statusDisplay
            });
        }
    }
}