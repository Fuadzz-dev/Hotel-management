package main.java.com.hotel.ui.customer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.java.com.hotel.model.User;

public class CustomerHomeFrame extends JFrame {
    private User user;
    private JPanel contentPanel;

    public CustomerHomeFrame(User user) {
        this.user = user;
        setTitle("Luxury Hotel - " + user.getFullName());
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        init();
    }

    private void init() {
        // Main panel dengan background overlay
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dark overlay
                g2d.setColor(new Color(0, 0, 0, 120));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel topBar = createTopBar();

        // Content area dengan glass effect
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
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Default panel
        contentPanel.add(new RoomDetailsPanel(), BorderLayout.CENTER);

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

        // LEFT
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        leftPanel.setOpaque(false);

        JLabel lblWelcome = new JLabel("Welcome, " + user.getFullName());
        lblWelcome.setForeground(new Color(212, 175, 55));
        lblWelcome.setFont(new Font("Serif", Font.BOLD, 26));
        leftPanel.add(lblWelcome);

        // CENTER - tombol navigasi
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 25));
        centerPanel.setOpaque(false);

        JButton btnRoomDetails = createNavButton("🏨 Room Details", new Color(52, 152, 219));
        JButton btnSearchRooms = createNavButton("🔎 Search Rooms", new Color(46, 204, 113)); // ← Tombol baru
        JButton btnCheckIn = createNavButton("📋 Check In", new Color(26, 188, 156));
        JButton btnHistory = createNavButton("📜 History", new Color(155, 89, 182));

        centerPanel.add(btnRoomDetails);
        centerPanel.add(btnSearchRooms); // ← Tambahkan ke UI
        centerPanel.add(btnCheckIn);
        centerPanel.add(btnHistory);

        // RIGHT
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 25));
        rightPanel.setOpaque(false);

        JButton btnLogout = createNavButton("🚪 Logout", new Color(231, 76, 60));
        JButton btnClose = createCloseButton();

        rightPanel.add(btnLogout);
        rightPanel.add(btnClose);

        // ADD TO TOP BAR
        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(centerPanel, BorderLayout.CENTER);
        topBar.add(rightPanel, BorderLayout.EAST);

        // EVENTS ------------------------------------------
        btnRoomDetails.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new RoomDetailsPanel(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        // Search Rooms Action (versi modern + fitur baru)
        btnSearchRooms.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new RoomSearchPanel(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
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
                    g2d.setColor(color.darker());
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

        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(160, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JButton createCloseButton() {
        JButton button = new JButton("✕") {
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
