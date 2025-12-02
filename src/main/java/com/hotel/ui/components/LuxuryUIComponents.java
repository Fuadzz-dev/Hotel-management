package main.java.com.hotel.ui.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Komponen UI dengan tema mewah dan elegan
 */
public class LuxuryUIComponents {
    
    // Warna tema mewah
    public static final Color GOLD = new Color(212, 175, 55);
    public static final Color DARK_GOLD = new Color(184, 134, 11);
    public static final Color CREAM = new Color(245, 245, 220);
    public static final Color DARK_NAVY = new Color(25, 25, 40);
    public static final Color NAVY_TRANSPARENT = new Color(25, 25, 40, 200);
    public static final Color BURGUNDY = new Color(128, 0, 32);
    public static final Color SILVER = new Color(192, 192, 192);
    
    // Font mewah
    public static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 28);
    public static final Font SUBTITLE_FONT = new Font("Serif", Font.PLAIN, 18);
    public static final Font BUTTON_FONT = new Font("Serif", Font.BOLD, 14);
    public static final Font LABEL_FONT = new Font("Serif", Font.PLAIN, 14);

    /**
     * Membuat tombol dengan style mewah
     */
    public static JButton createLuxuryButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(CREAM);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Border dengan efek emas
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(GOLD, 2, true),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(GOLD.brighter(), 3, true),
                    BorderFactory.createEmptyBorder(10, 25, 10, 25)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(GOLD, 2, true),
                    BorderFactory.createEmptyBorder(10, 25, 10, 25)
                ));
            }
        });
        
        return button;
    }

    /**
     * Membuat panel dengan background transparan gelap dan border emas
     */
    public static JPanel createLuxuryPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background transparan
                g2d.setColor(NAVY_TRANSPARENT);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Border emas dengan gradien
                GradientPaint gradient = new GradientPaint(
                    0, 0, GOLD,
                    getWidth(), getHeight(), DARK_GOLD
                );
                g2d.setPaint(gradient);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
                
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    /**
     * Membuat label judul dengan style mewah
     */
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(GOLD);
        
        // Shadow effect
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, DARK_GOLD),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
        
        return label;
    }

    /**
     * Membuat label dengan style mewah
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(CREAM);
        return label;
    }

    /**
     * Membuat text field dengan style mewah
     */
    public static JTextField createLuxuryTextField() {
        JTextField textField = new JTextField();
        textField.setFont(LABEL_FONT);
        textField.setBackground(new Color(245, 245, 220, 230));
        textField.setForeground(DARK_NAVY);
        textField.setCaretColor(DARK_NAVY);
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(GOLD, 2, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return textField;
    }

    /**
     * Membuat password field dengan style mewah
     */
    public static JPasswordField createLuxuryPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(LABEL_FONT);
        passwordField.setBackground(new Color(245, 245, 220, 230));
        passwordField.setForeground(DARK_NAVY);
        passwordField.setCaretColor(DARK_NAVY);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(GOLD, 2, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return passwordField;
    }

    /**
     * Membuat combo box dengan style mewah
     */
    public static JComboBox<String> createLuxuryComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(LABEL_FONT);
        comboBox.setBackground(CREAM);
        comboBox.setForeground(DARK_NAVY);
        comboBox.setBorder(new LineBorder(GOLD, 2, true));
        return comboBox;
    }

    /**
     * Membuat table dengan style mewah
     */
    public static void styleLuxuryTable(JTable table) {
        table.setFont(LABEL_FONT);
        table.setRowHeight(30);
        table.setBackground(new Color(245, 245, 220, 250));
        table.setForeground(DARK_NAVY);
        table.setSelectionBackground(new Color(212, 175, 55, 150));
        table.setSelectionForeground(DARK_NAVY);
        table.setGridColor(GOLD);
        table.setShowGrid(true);
        
        // Header style
        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 14));
        table.getTableHeader().setBackground(DARK_NAVY);
        table.getTableHeader().setForeground(GOLD);
        table.getTableHeader().setBorder(new LineBorder(GOLD, 2));
    }

    /**
     * Membuat scroll pane dengan style mewah
     */
    public static JScrollPane createLuxuryScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(new LineBorder(GOLD, 2, true));
        scrollPane.getViewport().setBackground(CREAM);
        return scrollPane;
    }

    /**
     * Panel header dengan gradien emas
     */
    public static JPanel createGradientHeaderPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, DARK_NAVY,
                    getWidth(), 0, new Color(40, 40, 60)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Garis emas di bawah
                g2d.setPaint(new GradientPaint(0, getHeight()-3, GOLD, getWidth(), getHeight()-3, DARK_GOLD));
                g2d.fillRect(0, getHeight()-3, getWidth(), 3);
                
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }
}