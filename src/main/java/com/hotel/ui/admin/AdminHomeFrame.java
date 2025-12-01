package main.java.com.hotel.ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.com.hotel.model.User;

public class AdminHomeFrame extends JFrame {
    private User user;

    public AdminHomeFrame(User user) {
        this.user = user;
        setTitle("Admin Dashboard - " + user.getFullName());
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    private void init() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(52, 73, 94));
        
        JLabel lblWelcome = new JLabel("Admin Panel - " + user.getFullName());
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(lblWelcome);

        JButton btnRoomMgmt = new JButton("Room Management");
        JButton btnUserInfo = new JButton("User Info");
        JButton btnFinanceReport = new JButton("Finance Report");
        JButton btnLogout = new JButton("Logout");

        btnRoomMgmt.setBackground(new Color(46, 204, 113));
        btnUserInfo.setBackground(new Color(52, 152, 219));
        btnFinanceReport.setBackground(new Color(155, 89, 182));
        btnLogout.setBackground(new Color(231, 76, 60));

        for (JButton btn : new JButton[]{btnRoomMgmt, btnUserInfo, btnFinanceReport, btnLogout}) {
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
        }

        topPanel.add(btnRoomMgmt);
        topPanel.add(btnUserInfo);
        topPanel.add(btnFinanceReport);
        topPanel.add(btnLogout);

        add(topPanel, BorderLayout.NORTH);

        // Default center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel lblDefault = new JLabel("Welcome to Admin Dashboard", SwingConstants.CENTER);
        lblDefault.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(lblDefault, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        btnRoomMgmt.addActionListener(e -> {
            new RoomManagementFrame().setVisible(true);
        });

        btnUserInfo.addActionListener(e -> {
            new UserInfoFrame().setVisible(true);
        });

        btnFinanceReport.addActionListener(e -> {
            new FinanceReportFrame().setVisible(true);
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