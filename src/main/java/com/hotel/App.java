package main.java.com.hotel;

import javax.swing.SwingUtilities;

import main.java.com.hotel.ui.login.LoginFrame;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
