package main.java.com.hotel.ui.login;

import java.awt.*;
import javax.swing.*;
import main.java.com.hotel.dao.UserDAO;
import main.java.com.hotel.model.User;
import main.java.com.hotel.ui.admin.AdminHomeFrame;
import main.java.com.hotel.ui.customer.CustomerHomeFrame;
import main.java.com.hotel.ui.components.LuxuryUIComponents;

public class LoginFrame extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginFrame() {
        setTitle("Luxury Hotel Management - Login");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set background gelap
        getContentPane().setBackground(LuxuryUIComponents.DARK_NAVY);
        
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        
        // Main luxury panel
        JPanel mainPanel = LuxuryUIComponents.createLuxuryPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.insets = new Insets(15, 25, 15, 25);
        panelGbc.fill = GridBagConstraints.HORIZONTAL;
        panelGbc.gridx = 0;
        
        // Logo/Icon Hotel (bisa diganti dengan gambar)
        panelGbc.gridy = 0;
        panelGbc.gridwidth = 2;
        JLabel lblLogo = new JLabel("🏨", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Serif", Font.PLAIN, 60));
        lblLogo.setForeground(LuxuryUIComponents.GOLD);
        mainPanel.add(lblLogo, panelGbc);
        
        // Title
        panelGbc.gridy = 1;
        JLabel lblTitle = LuxuryUIComponents.createTitleLabel("LUXURY HOTEL");
        mainPanel.add(lblTitle, panelGbc);
        
        // Subtitle
        panelGbc.gridy = 2;
        JLabel lblSubtitle = new JLabel("Experience Elegance", SwingConstants.CENTER);
        lblSubtitle.setFont(LuxuryUIComponents.SUBTITLE_FONT);
        lblSubtitle.setForeground(LuxuryUIComponents.CREAM);
        mainPanel.add(lblSubtitle, panelGbc);
        
        // Spacing
        panelGbc.gridy = 3;
        mainPanel.add(Box.createVerticalStrut(20), panelGbc);
        
        // Username
        panelGbc.gridy = 4;
        panelGbc.gridwidth = 1;
        panelGbc.anchor = GridBagConstraints.WEST;
        JLabel lblUsername = LuxuryUIComponents.createLabel("Username");
        mainPanel.add(lblUsername, panelGbc);
        
        panelGbc.gridy = 5;
        panelGbc.gridwidth = 2;
        tfUsername = LuxuryUIComponents.createLuxuryTextField();
        tfUsername.setPreferredSize(new Dimension(300, 40));
        mainPanel.add(tfUsername, panelGbc);
        
        // Password
        panelGbc.gridy = 6;
        panelGbc.gridwidth = 1;
        JLabel lblPassword = LuxuryUIComponents.createLabel("Password");
        mainPanel.add(lblPassword, panelGbc);
        
        panelGbc.gridy = 7;
        panelGbc.gridwidth = 2;
        pfPassword = LuxuryUIComponents.createLuxuryPasswordField();
        pfPassword.setPreferredSize(new Dimension(300, 40));
        mainPanel.add(pfPassword, panelGbc);
        
        // Spacing
        panelGbc.gridy = 8;
        mainPanel.add(Box.createVerticalStrut(20), panelGbc);
        
        // Login Button
        panelGbc.gridy = 9;
        btnLogin = LuxuryUIComponents.createLuxuryButton("LOGIN", LuxuryUIComponents.BURGUNDY);
        btnLogin.setPreferredSize(new Dimension(300, 45));
        mainPanel.add(btnLogin, panelGbc);
        
        // Spacing
        panelGbc.gridy = 10;
        mainPanel.add(Box.createVerticalStrut(10), panelGbc);
        
        // Register Button
        panelGbc.gridy = 11;
        btnRegister = LuxuryUIComponents.createLuxuryButton("CREATE ACCOUNT", LuxuryUIComponents.DARK_NAVY);
        btnRegister.setPreferredSize(new Dimension(300, 45));
        mainPanel.add(btnRegister, panelGbc);
        
        // Add main panel to frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mainPanel, gbc);
        
        // Event listeners
        btnLogin.addActionListener(e -> doLogin());
        btnRegister.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            this.dispose();
        });
        
        // Enter key support
        tfUsername.addActionListener(e -> pfPassword.requestFocus());
        pfPassword.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String user = tfUsername.getText().trim();
        String pass = new String(pfPassword.getPassword()).trim();
        
        if (user.isEmpty() || pass.isEmpty()) {
            showLuxuryMessage("Please fill in all fields", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        UserDAO dao = new UserDAO();
        User u = dao.login(user, pass);
        
        if (u != null) {
            showLuxuryMessage("Welcome, " + u.getFullName() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
            if ("ADMIN".equals(u.getRole())) {
                new AdminHomeFrame(u).setVisible(true);
            } else {
                new CustomerHomeFrame(u).setVisible(true);
            }
        } else {
            showLuxuryMessage("Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showLuxuryMessage(String message, String title, int messageType) {
        UIManager.put("OptionPane.background", LuxuryUIComponents.CREAM);
        UIManager.put("Panel.background", LuxuryUIComponents.CREAM);
        UIManager.put("OptionPane.messageForeground", LuxuryUIComponents.DARK_NAVY);
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}