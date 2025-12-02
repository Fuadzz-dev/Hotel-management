package main.java.com.hotel.ui.login;

import javax.swing.*;
import java.awt.*;
import main.java.com.hotel.dao.UserDAO;
import main.java.com.hotel.model.User;

public class RegisterFrame extends JFrame {

    private JTextField txtUsername, txtFullName;
    private JPasswordField txtPassword;
    private UserDAO userDAO;

    public RegisterFrame() {
        userDAO = new UserDAO();

        setTitle("Create New Account");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        init();
    }

    private void init() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtFullName = new JTextField();

        JButton btnRegister = new JButton("Register");

        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);

        panel.add(new JLabel("Full Name:"));
        panel.add(txtFullName);

        // Tombol daftar
        panel.add(new JLabel());
        panel.add(btnRegister);

        add(panel);

        btnRegister.addActionListener(e -> registerUser());
    }

    private void registerUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String fullName = txtFullName.getText();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        // Role otomatis CUSTOMER
        User user = new User(username, password, fullName, "CUSTOMER");

        if (userDAO.register(user)) {
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            this.dispose();
            new LoginFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create account!");
        }
    }
}
