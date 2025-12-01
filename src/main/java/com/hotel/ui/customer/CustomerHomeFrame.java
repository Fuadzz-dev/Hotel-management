package main.java.com.hotel.ui.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.com.hotel.model.User;

public class CustomerHomeFrame extends JFrame {
    private User user;
    
    public CustomerHomeFrame(User user){
        this.user = user;
        setTitle("Customer Home - " + user.getFullName());
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }
    
    private void init(){
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(new Color(52, 73, 94));
        
        JLabel lblWelcome = new JLabel("Welcome, " + user.getFullName() + "!");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(lblWelcome);
        
        JButton btnRoomDetails = new JButton("Room Details");
        JButton btnCheckIn = new JButton("Check In");
        JButton btnHistory = new JButton("Check History");
        JButton btnLogout = new JButton("Logout");

        btnRoomDetails.setBackground(new Color(52, 152, 219));
        btnCheckIn.setBackground(new Color(46, 204, 113));
        btnHistory.setBackground(new Color(155, 89, 182));
        btnLogout.setBackground(new Color(231, 76, 60));

        for (JButton btn : new JButton[]{btnRoomDetails, btnCheckIn, btnHistory, btnLogout}) {
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
        }

        top.add(btnRoomDetails);
        top.add(btnCheckIn);
        top.add(btnHistory);
        top.add(btnLogout);
        add(top, BorderLayout.NORTH);

        // default center panel
        add(new RoomDetailsPanel(), BorderLayout.CENTER);

        btnRoomDetails.addActionListener(e -> {
            getContentPane().removeAll();
            add(top, BorderLayout.NORTH);
            add(new RoomDetailsPanel(), BorderLayout.CENTER);
            revalidate(); 
            repaint();
        });

        btnCheckIn.addActionListener(e -> {
            new CheckInFrame(user).setVisible(true);
        });

        btnHistory.addActionListener(e -> {
            new CheckHistoryFrame(user).setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", 
                "Logout", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new main.java.com.hotel.ui.login.LoginFrame().setVisible(true);
            }
        });
    }
}