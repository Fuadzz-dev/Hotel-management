package main.java.com.hotel.ui.customer;

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

import main.java.com.hotel.dao.ReservationDAO;
import main.java.com.hotel.model.Reservation;
import main.java.com.hotel.model.User;

public class CheckHistoryFrame extends JFrame {
    private User user;
    private JTable table;
    private DefaultTableModel model;
    private ReservationDAO reservationDAO;

    public CheckHistoryFrame(User user) {
        this.user = user;
        this.reservationDAO = new ReservationDAO();
        
        setTitle("My Reservation History");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        loadData();
    }

    private void init() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(52, 73, 94));
        JLabel lblTitle = new JLabel("My Booking History - " + user.getFullName());
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(lblTitle);

        String[] columns = {"ID", "Room No", "Check In", "Check Out", "Total Price", "Status"};
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
        JButton btnCancel = new JButton("Cancel Booking");
        
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnCancel.setBackground(new Color(231, 76, 60));
        
        for (JButton btn : new JButton[]{btnRefresh, btnCancel}) {
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
        }

        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnCancel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
        btnCancel.addActionListener(e -> cancelBooking());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Reservation> reservations = reservationDAO.findByUserId(user.getId());
        
        for (Reservation r : reservations) {
            model.addRow(new Object[]{
                r.getId(),
                r.getRoomNumber(),
                r.getCheckIn().toString(),
                r.getCheckOut().toString(),
                String.format("Rp %.0f", r.getTotalPrice()),
                r.getStatus()
            });
        }
    }

    private void cancelBooking() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String status = (String) model.getValueAt(selectedRow, 5);
        if ("CANCELLED".equals(status)) {
            JOptionPane.showMessageDialog(this, "This booking is already cancelled!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if ("CHECKED_OUT".equals(status)) {
            JOptionPane.showMessageDialog(this, "Cannot cancel a completed booking!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel this booking?", 
            "Confirm Cancellation", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int reservationId = (int) model.getValueAt(selectedRow, 0);
            if (reservationDAO.updateStatus(reservationId, "CANCELLED")) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}