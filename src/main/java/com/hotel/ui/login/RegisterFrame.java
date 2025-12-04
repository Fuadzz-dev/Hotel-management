package main.java.com.hotel.ui.login;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import main.java.com.hotel.dao.UserDAO;
import main.java.com.hotel.model.User;
import main.java.com.hotel.ui.components.LuxuryUIComponents;

public class RegisterFrame extends JFrame {

    private JTextField tfFullName;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnRegister;
    private JButton btnLogin;

    public RegisterFrame() {
        setTitle("Luxury Hotel Management - Register");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(LuxuryUIComponents.DARK_NAVY);

        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        // Main Panel
        JPanel mainPanel = LuxuryUIComponents.createLuxuryPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.insets = new Insets(15, 25, 15, 25);
        panelGbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        panelGbc.gridwidth = 2;
        JLabel lblLogo = new JLabel("📝", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Serif", Font.PLAIN, 60));
        lblLogo.setForeground(LuxuryUIComponents.GOLD);
        mainPanel.add(lblLogo, panelGbc);

        // Title
        panelGbc.gridy = 1;
        JLabel lblTitle = LuxuryUIComponents.createTitleLabel("CREATE ACCOUNT");
        mainPanel.add(lblTitle, panelGbc);

        // Subtitle
        panelGbc.gridy = 2;
        JLabel lblSubtitle = new JLabel("Join Luxury Hotel", SwingConstants.CENTER);
        lblSubtitle.setFont(LuxuryUIComponents.SUBTITLE_FONT);
        lblSubtitle.setForeground(LuxuryUIComponents.CREAM);
        mainPanel.add(lblSubtitle, panelGbc);

        // Spacing
        panelGbc.gridy = 3;
        mainPanel.add(Box.createVerticalStrut(20), panelGbc);

        // Fullname
        panelGbc.gridy = 4;
        panelGbc.gridwidth = 1;
        JLabel lblFullName = LuxuryUIComponents.createLabel("Full Name");
        mainPanel.add(lblFullName, panelGbc);

        panelGbc.gridy = 5;
        panelGbc.gridwidth = 2;
        tfFullName = LuxuryUIComponents.createLuxuryTextField();
        tfFullName.setPreferredSize(new Dimension(300, 40));
        mainPanel.add(tfFullName, panelGbc);

        // Username
        panelGbc.gridy = 6;
        panelGbc.gridwidth = 1;
        JLabel lblUsername = LuxuryUIComponents.createLabel("Username");
        mainPanel.add(lblUsername, panelGbc);

        panelGbc.gridy = 7;
        panelGbc.gridwidth = 2;
        tfUsername = LuxuryUIComponents.createLuxuryTextField();
        tfUsername.setPreferredSize(new Dimension(300, 40));
        mainPanel.add(tfUsername, panelGbc);

        // Password
        panelGbc.gridy = 8;
        panelGbc.gridwidth = 1;
        JLabel lblPassword = LuxuryUIComponents.createLabel("Password");
        mainPanel.add(lblPassword, panelGbc);

        panelGbc.gridy = 9;
        panelGbc.gridwidth = 2;
        pfPassword = LuxuryUIComponents.createLuxuryPasswordField();
        pfPassword.setPreferredSize(new Dimension(300, 40));
        mainPanel.add(pfPassword, panelGbc);

        // Spacing
        panelGbc.gridy = 10;
        mainPanel.add(Box.createVerticalStrut(20), panelGbc);

        // Register Button
        panelGbc.gridy = 11;
        btnRegister = LuxuryUIComponents.createLuxuryButton("REGISTER", LuxuryUIComponents.BURGUNDY);
        btnRegister.setPreferredSize(new Dimension(300, 45));
        mainPanel.add(btnRegister, panelGbc);

        // Spacing
        panelGbc.gridy = 12;
        mainPanel.add(Box.createVerticalStrut(10), panelGbc);

        // Back to Login Button
        panelGbc.gridy = 13;
        btnLogin = LuxuryUIComponents.createLuxuryButton("BACK TO LOGIN", LuxuryUIComponents.DARK_NAVY);
        btnLogin.setPreferredSize(new Dimension(300, 45));
        mainPanel.add(btnLogin, panelGbc);

        // ADD Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mainPanel, gbc);

        // Event
        btnRegister.addActionListener(e -> doRegister());
        btnLogin.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        // Enter key
        tfFullName.addActionListener(e -> tfUsername.requestFocus());
        tfUsername.addActionListener(e -> pfPassword.requestFocus());
        pfPassword.addActionListener(e -> doRegister());
    }

    private void doRegister() {
        String fullName = tfFullName.getText().trim();
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword());

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showLuxuryMessage("All fields are required!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User u = new User(fullName, username, password, "CUSTOMER");

        UserDAO dao = new UserDAO();
        boolean success = dao.register(u);

        if (success) {
            showLuxuryMessage("Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame().setVisible(true);
            dispose();
        } else {
            showLuxuryMessage("Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showLuxuryMessage(String msg, String title, int type) {
        UIManager.put("OptionPane.background", LuxuryUIComponents.CREAM);
        UIManager.put("Panel.background", LuxuryUIComponents.CREAM);
        UIManager.put("OptionPane.messageForeground", LuxuryUIComponents.DARK_NAVY);

        JOptionPane.showMessageDialog(this, msg, title, type);
    }
}
