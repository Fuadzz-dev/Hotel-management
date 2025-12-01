package main.java.com.hotel.ui.customer;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class RoomDetailsPanel extends JScrollPane {
    private JTable table;
    private DefaultTableModel model;
    public RoomDetailsPanel() {
        String[] cols = {"ID","No Room","Jenis Kamar","Price/day","Status"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        setViewportView(table);
        loadData();
    }
    private void loadData() {
        RoomDAO dao = new RoomDAO();
        List<Room> rooms = dao.findAll();
        model.setRowCount(0);
        for (Room r : rooms) {
            model.addRow(new Object[]{r.getId(), r.getRoomNumber(), r.getRoomType(), r.getPricePerDay(), r.getStatus()});
        }
    }
}
