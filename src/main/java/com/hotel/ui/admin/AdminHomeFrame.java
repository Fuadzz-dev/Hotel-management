package main.java.com.hotel.ui.admin;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.java.com.hotel.model.User;

public class AdminHomeFrame extends JFrame {
    private User user;
    private JPanel contentPanel;

    public AdminHomeFrame(User user) {
        this.user = user;
        setTitle("Admin Dashboard - " + user.getFullName());
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 120));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel topBar = createTopBar();

        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 80));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setOpaque(false);

        JLabel lblTitle = new JLabel("ADMIN DASHBOARD");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 52));
        lblTitle.setForeground(new Color(212, 175, 55));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Luxury Hotel Management System");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 20));
        lblSubtitle.setForeground(new Color(255, 255, 255, 200));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDivider = new JLabel("━━━━━━━━━━━━━━━━━━━━");
        lblDivider.setFont(new Font("Arial", Font.PLAIN, 20));
        lblDivider.setForeground(new Color(212, 175, 55, 150));
        lblDivider.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(lblTitle);
        welcomePanel.add(Box.createVerticalStrut(15));
        welcomePanel.add(lblSubtitle);
        welcomePanel.add(Box.createVerticalStrut(15));
        welcomePanel.add(lblDivider);

        contentPanel.add(welcomePanel);

        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(20, 20, 30, 230),
                    0, getHeight(), new Color(40, 40, 50, 230)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(212, 175, 55));
                g2d.fillRect(0, getHeight() - 3, getWidth(), 3);
            }
        };
        topBar.setLayout(new BorderLayout());
        topBar.setPreferredSize(new Dimension(1400, 100));
        topBar.setOpaque(false);

        // LEFT PANEL - Admin Info
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        leftPanel.setOpaque(false);

        JLabel lblAdmin = new JLabel("👑 Admin Panel - " + user.getFullName());
        lblAdmin.setForeground(new Color(212, 175, 55));
        lblAdmin.setFont(new Font("Serif", Font.BOLD, 24));
        leftPanel.add(lblAdmin);

        // CENTER PANEL - Navigation Buttons (4 TOMBOL)
        JPanel centerPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        centerPanel.setOpaque(false);


        JButton btnRoomMgmt = createNavButton("Room Mgmt", new Color(46, 204, 113));
        JButton btnGuestMgmt = createNavButton("Guest Mgmt", new Color(52, 152, 219));
        JButton btnUserInfo = createNavButton("User Info", new Color(155, 89, 182));
        JButton btnFinanceReport = createNavButton("Finance", new Color(230, 126, 34));

        centerPanel.add(btnRoomMgmt);
        centerPanel.add(btnGuestMgmt);
        centerPanel.add(btnUserInfo);
        centerPanel.add(btnFinanceReport);

        // RIGHT PANEL - Logout & Close
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 25));
        rightPanel.setOpaque(false);

        JButton btnLogout = createNavButton("Logout", new Color(231, 76, 60));
        JButton btnClose = createCloseButton();

        rightPanel.add(btnLogout);
        rightPanel.add(btnClose);

        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(centerPanel, BorderLayout.CENTER);
        topBar.add(rightPanel, BorderLayout.EAST);

        // ===== EVENT LISTENERS =====
        btnRoomMgmt.addActionListener(e -> new RoomManagementFrame().setVisible(true));
        btnGuestMgmt.addActionListener(e -> new GuestManagementFrame().setVisible(true));
        btnUserInfo.addActionListener(e -> new UserInfoFrame().setVisible(true));
        btnFinanceReport.addActionListener(e -> new FinanceReportFrame().setVisible(true));

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new main.java.com.hotel.ui.login.LoginFrame().setVisible(true);
            }
        });

        return topBar;
    }

    private JButton createNavButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(color.darker().darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color.brighter());
                } else {
                    g2d.setColor(color);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                if (getModel().isRollover()) {
                    g2d.setColor(new Color(212, 175, 55));
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 15, 15);
                }

                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createCloseButton() {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(new Color(192, 57, 43));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(231, 76, 60));
                } else {
                    g2d.setColor(new Color(231, 76, 60, 150));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(45, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> System.exit(0));
        return button;
    }
}