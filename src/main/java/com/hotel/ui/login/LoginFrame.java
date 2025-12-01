package main.java.com.hotel.ui.login;

import com.hotel.dao.UserDAO;
import com.hotel.model.User;
import com.hotel.ui.admin.AdminHomeFrame;
import com.hotel.ui.customer.CustomerHomeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Hotel Management - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }

    private void init() {
        JPanel panel = new JPanel(new GridLayout(4,1,5,5));
        tfUsername = new JTextField();
        pfPassword = new JPasswordField();
        btnLogin = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(tfUsername);
        panel.add(new JLabel("Password:"));
        panel.add(pfPassword);

        add(panel, BorderLayout.CENTER);
        add(btnLogin, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String user = tfUsername.getText().trim();
        String pass = new String(pfPassword.getPassword()).trim();
        UserDAO dao = new UserDAO();
        User u = dao.login(user, pass);
        if (u != null) {
            JOptionPane.showMessageDialog(this, "Welcome " + u.getFullName());
            dispose();
            if ("ADMIN".equals(u.getRole())) {
                new AdminHomeFrame(u).setVisible(true);
            } else {
                new CustomerHomeFrame(u).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
