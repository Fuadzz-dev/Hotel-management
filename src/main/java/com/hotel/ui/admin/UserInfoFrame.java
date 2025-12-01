package main.java.com.hotel.ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.com.hotel.dao.ReservationDAO;
import main.java.com.hotel.model.Reservation;

public class UserInfoFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private ReservationDAO reservationDAO;

    public UserInfoFrame() {
        reservationDAO = new ReservationDAO();
        setTitle("User Reservations Info");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        loadData();
    }

    private void init() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(52, 73, 94));
        JLabel lblTitle = new JLabel("All User Reservations");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(lblTitle);

        String[] columns = {"ID", "User Name", "Room No", "Check In", "Check Out", "Total Price", "Status"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };
        table = new JTable(model);
        String[] statusOptions = {"BOOKED", "CHECKED_IN", "CHECKED_OUT", "CANCELLED"};
        javax.swing.JComboBox<String> comboStatus = new javax.swing.JComboBox<>(statusOptions);
        table.getColumnModel().getColumn(6).setCellEditor(new javax.swing.DefaultCellEditor(comboStatus));
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            // hanya kolom status (index ke 6)
            if (column == 6) {
                int reservationId = (int) model.getValueAt(row, 0); // kolom ID
                String newStatus = (String) model.getValueAt(row, 6);
                // update DB
                boolean success = reservationDAO.updateStatus(reservationId, newStatus);
                if (success) {
                    System.out.println("Status updated for ID " + reservationId + " to " + newStatus);
                } else {
                    System.out.println("Failed to update status!");
                }
            }
        });
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 12));
        buttonPanel.add(btnRefresh);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Reservation> reservations = reservationDAO.findAll();
        for (Reservation r : reservations) {
            model.addRow(new Object[]{
                r.getId(),
                r.getUserName(),
                r.getRoomNumber(),
                r.getCheckIn().toString(),
                r.getCheckOut().toString(),
                String.format("Rp %.0f", r.getTotalPrice()),
                r.getStatus()
            });
        }
    }
}