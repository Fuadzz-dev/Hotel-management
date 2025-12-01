package main.java.com.hotel.ui.customer;

import com.hotel.model.User;
import javax.swing.*;
import java.awt.*;

public class CustomerHomeFrame extends JFrame {
    private User user;
    public CustomerHomeFrame(User user){
        this.user = user;
        setTitle("Customer Home - " + user.getFullName());
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }
    private void init(){
        JPanel top = new JPanel();
        top.add(new JLabel("Hello, " + user.getFullName()));
        JButton btnRoomDetails = new JButton("Room Details");
        JButton btnCheckIn = new JButton("Check In");
        JButton btnHistory = new JButton("Check History");
        JButton btnLogout = new JButton("Logout");

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
            revalidate(); repaint();
        });

        // other buttons: open frames/panels (CheckInFrame, CheckHistoryFrame)
        btnLogout.addActionListener(e -> {
            dispose();
            new com.hotel.ui.login.LoginFrame().setVisible(true);
        });
    }
}
