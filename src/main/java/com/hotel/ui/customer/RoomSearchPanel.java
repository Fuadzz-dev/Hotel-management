package main.java.com.hotel.ui.customer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.*;
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

    // default luxury colors (fallback)
    private final Color GOLD_FALLBACK = new Color(212, 175, 55);
    private final Color CREAM_FALLBACK = new Color(250, 245, 240);

    // resolved at runtime via reflection (may fallback)
    private final Color GOLD;
    private final Color CREAM;

    public RoomSearchPanel() {
        roomDAO = new RoomDAO();
        setLayout(new BorderLayout());

        // try to read colors from LuxuryUIComponents if they exist, otherwise fallback
        GOLD = reflectGetColor("main.java.com.hotel.ui.components.LuxuryUIComponents", "GOLD", GOLD_FALLBACK);
        CREAM = reflectGetColor("main.java.com.hotel.ui.components.LuxuryUIComponents", "CREAM", CREAM_FALLBACK);

        setBackground(CREAM);

        init();
        loadAllRooms();
    }

    private void init() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(CREAM);
        searchPanel.setBorder(BorderFactory.createLineBorder(GOLD, 2));

        // Label + controls (styled)
        searchPanel.add(createGoldLabel("Room Type:"));
        cbRoomType = new JComboBox<>(new String[]{"ALL", "STANDARD", "DELUXE", "SUITE"});
        styleCombo(cbRoomType);
        searchPanel.add(cbRoomType);

        searchPanel.add(createGoldLabel("Status:"));
        cbStatus = new JComboBox<>(new String[]{"ALL", "AVAILABLE", "OCCUPIED", "MAINTENANCE"});
        styleCombo(cbStatus);
        searchPanel.add(cbStatus);

        searchPanel.add(createGoldLabel("Max Price:"));
        tfMaxPrice = new JTextField(10);
        styleTextField(tfMaxPrice);
        searchPanel.add(tfMaxPrice);

        JButton btnSearch = createGoldButton("Search");
        JButton btnReset = createRedButton("Reset");
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        add(searchPanel, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Room Number", "Type", "Price/Day", "Status"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        // try to call LuxuryUIComponents.styleLuxuryTable(table) via reflection if available
        reflectInvokeStyleTable("main.java.com.hotel.ui.components.LuxuryUIComponents", "styleLuxuryTable", table);
        // if reflection failed, apply a simple style fallback
        simpleStyleTableFallback(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(GOLD, 2));
        scrollPane.getViewport().setBackground(CREAM);

        add(scrollPane, BorderLayout.CENTER);

        // actions
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
            model.addRow(formatRoomRow(r));
        }
    }

    private void searchRooms() {
        model.setRowCount(0);
        List<Room> rooms = roomDAO.findAll();

        String type = (String) cbRoomType.getSelectedItem();
        String status = (String) cbStatus.getSelectedItem();
        String maxPriceText = tfMaxPrice.getText().trim();

        double maxPrice;
        try {
            maxPrice = maxPriceText.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceText);
        } catch (NumberFormatException ex) {
            // invalid number -> treat as no limit
            maxPrice = Double.MAX_VALUE;
        }

        for (Room r : rooms) {
            boolean matchType = "ALL".equals(type) || r.getRoomType().equals(type);
            boolean matchStatus = "ALL".equals(status) || r.getStatus().equals(status);
            boolean matchPrice = r.getPricePerDay() <= maxPrice;

            if (matchType && matchStatus && matchPrice) {
                model.addRow(formatRoomRow(r));
            }
        }
    }

    private Object[] formatRoomRow(Room r) {
        String price = String.format("Rp %,.0f", r.getPricePerDay());

        String status = r.getStatus();
        String statusDisplay;
        // classic switch for compatibility
        switch (status) {
            case "AVAILABLE":
                statusDisplay = "AVAILABLE";
                break;
            case "OCCUPIED":
                statusDisplay = "OCCUPIED";
                break;
            case "MAINTENANCE":
                statusDisplay = "MAINTENANCE";
                break;
            default:
                statusDisplay = status;
                break;
        }

        return new Object[]{
            r.getId(),
            r.getRoomNumber(),
            r.getRoomType(),
            price,
            statusDisplay
        };
    }

    // -------------------------
    // Reflection helpers
    // -------------------------
    private Color reflectGetColor(String className, String fieldName, Color fallback) {
        try {
            Class<?> cls = Class.forName(className);
            Field f = cls.getField(fieldName);
            Object val = f.get(null);
            if (val instanceof Color) return (Color) val;
        } catch (Exception ignored) {}
        return fallback;
    }

    private void reflectInvokeStyleTable(String className, String methodName, JTable table) {
        try {
            Class<?> cls = Class.forName(className);
            Method m = cls.getMethod(methodName, JTable.class);
            m.invoke(null, table); // static method expected
        } catch (Exception ignored) {}
    }

    // -------------------------
    // Local small styling helpers (fallbacks)
    // -------------------------
    private JLabel createGoldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(GOLD);
        return lbl;
    }

   private void styleCombo(JComboBox<String> combo) {
    combo.setFont(new Font("Arial", Font.PLAIN, 13));
    combo.setPrototypeDisplayValue("XXXXXXXXXXXX"); // ✔ aman
}


    private void styleTextField(JTextField tf) {
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
    }

    private JButton createGoldButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(GOLD);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        return b;
    }

    private JButton createRedButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(231, 76, 60));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        return b;
    }

    private void simpleStyleTableFallback(JTable t) {
        t.setRowHeight(28);
        t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        t.setFont(new Font("Arial", Font.PLAIN, 13));
    }
}
