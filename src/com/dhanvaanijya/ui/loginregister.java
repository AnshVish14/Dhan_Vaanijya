package com.dhanvaanijya.ui;

import javax.swing.*;
import java.awt.*;


public class loginregister extends JFrame {
    private final Color backgroundColor = Color.WHITE;
    private final Color primaryBlue = new Color(0, 102, 204);
    private final Color blackColor = Color.BLACK;
    private final Color buttonHoverColor = new Color(30, 144, 255);

    public loginregister() {
        setTitle("Dhanvaanijya Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(createLoginPanel());
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel labelTitle = new JLabel("Login to Dhanvaanijya", SwingConstants.CENTER);
        styleHeading(labelTitle);

        JLabel labelUser = new JLabel("Username:");
        labelUser.setForeground(blackColor);
        JTextField tfUser = new JTextField(20);
        styleTextField(tfUser);

        JLabel labelPass = new JLabel("Password:");
        labelPass.setForeground(blackColor);
        JPasswordField pfPass = new JPasswordField(20);
        styleTextField(pfPass);

        JButton btnLogin = new JButton("Login");
        styleButton(btnLogin, primaryBlue, Color.BLACK);

        btnLogin.addActionListener(e -> {
            String username = tfUser.getText().trim();
            String password = new String(pfPass.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            } else {
                // Proceed to dashboard
                this.dispose(); // Close login window
                SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
            }
        });

        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(labelTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelUser, gbc);
        gbc.gridx = 1;
        panel.add(tfUser, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelPass, gbc);
        gbc.gridx = 1;
        panel.add(pfPass, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        return panel;
    }

    private void styleTextField(JTextField tf) {
        tf.setBackground(Color.WHITE);
        tf.setForeground(Color.BLACK);
        tf.setCaretColor(Color.BLACK);
        tf.setBorder(BorderFactory.createLineBorder(primaryBlue));
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonHoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bg);
            }
        });
    }

    private void styleHeading(JLabel label) {
        label.setOpaque(true);
        label.setBackground(primaryBlue);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(350, 40));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new loginregister());
    }
}
