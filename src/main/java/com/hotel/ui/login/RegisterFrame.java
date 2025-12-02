package main.java.com.hotel.ui.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import main.java.com.hotel.dao.UserDAO;
import main.java.com.hotel.model.User;

public class RegisterFrame extends JFrame {
    private JTextField txtUsername, txtFullName;
    private JPasswordField txtPassword;
    private UserDAO userDAO;

    public RegisterFrame() {
        userDAO = new UserDAO();
        setTitle("Create Luxury Account");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        init();
    }

    private void init() {
        // Main panel dengan overlay
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Overlay gelap
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Register card
        JPanel registerCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                
                g2d.setColor(new Color(212, 175, 55, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 30, 30);
            }
        };
        registerCard.setLayout(new BorderLayout(0, 30));
        registerCard.setOpaque(false);
        registerCard.setBorder(new EmptyBorder(50, 60, 50, 60));
        registerCard.setPreferredSize(new Dimension(450, 600));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel("CREATE ACCOUNT");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 38));
        lblTitle.setForeground(new Color(212, 175, 55));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Join Our Luxury Experience");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        lblSubtitle.setForeground(new Color(255, 255, 255, 200));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDivider = new JLabel("━━━━━━━━━━");
        lblDivider.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDivider.setForeground(new Color(212, 175, 55, 150));
        lblDivider.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblSubtitle);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(lblDivider);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblFullName = new JLabel("Full Name");
        lblFullName.setFont(new Font("Arial", Font.BOLD, 14));
        lblFullName.setForeground(new Color(255, 255, 255, 220));
        formPanel.add(lblFullName, gbc);

        gbc.gridy = 1;
        txtFullName = createStyledTextField();
        formPanel.add(txtFullName, gbc);

        // Username
        gbc.gridy = 2;
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsername.setForeground(new Color(255, 255, 255, 220));
        formPanel.add(lblUsername, gbc);

        gbc.gridy = 3;
        txtUsername = createStyledTextField();
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy = 4;
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        lblPassword.setForeground(new Color(255, 255, 255, 220));
        formPanel.add(lblPassword, gbc);

        gbc.gridy = 5;
        txtPassword = createStyledPasswordField();
        formPanel.add(txtPassword, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        buttonPanel.setOpaque(false);

        JButton btnRegister = createLuxuryButton("REGISTER NOW", new Color(46, 204, 113), new Color(39, 174, 96));
        JButton btnBack = createLuxuryButton("BACK TO LOGIN", new Color(52, 73, 94), new Color(44, 62, 80));

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        registerCard.add(headerPanel, BorderLayout.NORTH);
        registerCard.add(formPanel, BorderLayout.CENTER);
        registerCard.add(buttonPanel, BorderLayout.SOUTH);

        // Close button
        JButton btnClose = new JButton("✕");
        btnClose.setFont(new Font("Arial", Font.BOLD, 20));
        btnClose.setForeground(Color.WHITE);
        btnClose.setBackground(new Color(231, 76, 60, 150));
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setPreferredSize(new Dimension(50, 50));
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(btnClose);

        // Layout
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1200, 700));
        
        mainPanel.setBounds(0, 0, 1200, 700);
        registerCard.setBounds(375, 50, 450, 600);
        topPanel.setBounds(0, 0, 1200, 60);
        
        layeredPane.add(mainPanel, Integer.valueOf(0));
        layeredPane.add(registerCard, Integer.valueOf(1));
        layeredPane.add(topPanel, Integer.valueOf(2));
        
        setContentPane(layeredPane);

        // Event handlers
        btnRegister.addActionListener(e -> registerUser());
        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        getRootPane().setDefaultButton(btnRegister);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(new Color(255, 255, 255, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(212, 175, 55, 100), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(new Color(255, 255, 255, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(212, 175, 55, 100), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }

    private JButton createLuxuryButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(hoverColor);
                } else {
                    g2d.setColor(bgColor);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void registerUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String fullName = txtFullName.getText().trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "All fields are required!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = new User(username, password, fullName, "CUSTOMER");

        if (userDAO.register(user)) {
            JOptionPane.showMessageDialog(this, 
                "Account created successfully!\nWelcome to our luxury hotel.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to create account!\nUsername may already exist.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}