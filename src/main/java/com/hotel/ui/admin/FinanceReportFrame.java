package main.java.com.hotel.ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import main.java.com.hotel.dao.ReservationDAO;
import main.java.com.hotel.model.Reservation;

public class FinanceReportFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private ReservationDAO reservationDAO;
    private JLabel lblTotalRevenue;

    public FinanceReportFrame() {
        reservationDAO = new ReservationDAO();
        setTitle("Finance Report");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        loadData();
    }

    private void init() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(52, 73, 94));
        JLabel lblTitle = new JLabel("Financial Report");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(lblTitle);

        String[] columns = {"ID", "User", "Room", "Check In", "Check Out", "Total Price", "Status"};
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

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblTotalRevenue = new JLabel("Total Revenue:");
        lblTotalRevenue.setFont(new Font("Arial", Font.BOLD, 20));
        lblTotalRevenue.setForeground(new Color(46, 204, 113));
        lblTotalRevenue.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 12));

        bottomPanel.add(lblTotalRevenue, BorderLayout.CENTER);
        bottomPanel.add(btnRefresh, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Reservation> reservations = reservationDAO.findAll();
        
        for (Reservation r : reservations) {
            if (!"CHECKED_OUT".equals(r.getStatus())) continue;
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

        double totalRevenue = reservationDAO.getTotalRevenue();
        lblTotalRevenue.setText(String.format("Total Revenue: Rp %.0f", totalRevenue));
    }
}