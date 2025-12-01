package main.java.com.hotel.ui.customer;

import com.hotel.dao.ReservationDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.model.User;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CheckInFrame extends JFrame {
    private User user;
    private JComboBox<String> cbRooms;
    private JTextField tfCheckIn;
    private JTextField tfCheckOut;
    private JLabel lblTotalPrice;
    private RoomDAO roomDAO;
    private ReservationDAO reservationDAO;
    private List<Room> availableRooms;

    public CheckInFrame(User user) {
        this.user = user;
        this.roomDAO = new RoomDAO();
        this.reservationDAO = new ReservationDAO();
        
        setTitle("Check In - Book a Room");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel lblTitle = new JLabel("Book Your Room");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Room Selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Select Room:"), gbc);

        availableRooms = roomDAO.findAll();
        cbRooms = new JComboBox<>();
        for (Room r : availableRooms) {
            if ("AVAILABLE".equals(r.getStatus())) {
                cbRooms.addItem(r.getRoomNumber() + " - " + r.getRoomType() + 
                               " (Rp " + String.format("%.0f", r.getPricePerDay()) + "/day)");
            }
        }
        gbc.gridx = 1;
        mainPanel.add(cbRooms, gbc);

        // Check-in Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Check-in Date (YYYY-MM-DD):"), gbc);

        tfCheckIn = new JTextField(LocalDate.now().toString());
        gbc.gridx = 1;
        mainPanel.add(tfCheckIn, gbc);

        // Check-out Date
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Check-out Date (YYYY-MM-DD):"), gbc);

        tfCheckOut = new JTextField(LocalDate.now().plusDays(1).toString());
        gbc.gridx = 1;
        mainPanel.add(tfCheckOut, gbc);

        // Calculate Button
        JButton btnCalculate = new JButton("Calculate Total");
        btnCalculate.setBackground(new Color(52, 152, 219));
        btnCalculate.setForeground(Color.WHITE);
        btnCalculate.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(btnCalculate, gbc);

        // Total Price Label
        lblTotalPrice = new JLabel("Total: Rp 0");
        lblTotalPrice.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalPrice.setForeground(new Color(46, 204, 113));
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblTotalPrice, gbc);

        // Book Button
        JButton btnBook = new JButton("Book Now");
        btnBook.setBackground(new Color(46, 204, 113));
        btnBook.setForeground(Color.WHITE);
        btnBook.setFocusPainted(false);
        btnBook.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 6;
        mainPanel.add(btnBook, gbc);

        add(mainPanel);

        btnCalculate.addActionListener(e -> calculateTotal());
        btnBook.addActionListener(e -> bookRoom());
    }

    private void calculateTotal() {
        try {
            int selectedIndex = cbRooms.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a room!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate checkIn = LocalDate.parse(tfCheckIn.getText().trim());
            LocalDate checkOut = LocalDate.parse(tfCheckOut.getText().trim());

            if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Find the corresponding available room
            int roomIndex = 0;
            for (Room r : availableRooms) {
                if ("AVAILABLE".equals(r.getStatus())) {
                    if (roomIndex == selectedIndex) {
                        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
                        double total = days * r.getPricePerDay();
                        lblTotalPrice.setText(String.format("Total: Rp %.0f (%d days)", total, days));
                        break;
                    }
                    roomIndex++;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bookRoom() {
        try {
            int selectedIndex = cbRooms.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a room!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate checkIn = LocalDate.parse(tfCheckIn.getText().trim());
            LocalDate checkOut = LocalDate.parse(tfCheckOut.getText().trim());

            if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Find the selected room
            int roomIndex = 0;
            Room selectedRoom = null;
            for (Room r : availableRooms) {
                if ("AVAILABLE".equals(r.getStatus())) {
                    if (roomIndex == selectedIndex) {
                        selectedRoom = r;
                        break;
                    }
                    roomIndex++;
                }
            }

            if (selectedRoom == null) {
                JOptionPane.showMessageDialog(this, "Room not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long days = ChronoUnit.DAYS.between(checkIn, checkOut);
            double total = days * selectedRoom.getPricePerDay();

            Reservation reservation = new Reservation();
            reservation.setUserId(user.getId());
            reservation.setRoomId(selectedRoom.getId());
            reservation.setCheckIn(checkIn);
            reservation.setCheckOut(checkOut);
            reservation.setTotalPrice(total);
            reservation.setStatus("BOOKED");

            if (reservationDAO.createReservation(reservation)) {
                roomDAO.updateStatus(selectedRoom.getId(), "OCCUPIED");
                JOptionPane.showMessageDialog(this, "Room booked successfully!\nTotal: Rp " + String.format("%.0f", total));
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to book room!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 