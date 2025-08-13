package com.dhanvaanijya.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class AboutUs extends JPanel {

    public AboutUs() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ---- Header Title ----
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(70, 130, 180), getWidth(), getHeight(), new Color(100, 149, 237)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(getWidth(), 70));
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel title = new JLabel("📘 About Dhan Vaanijya");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title);
        add(header, BorderLayout.NORTH);

        // ---- Info Content ----
        JTextArea aboutText = new JTextArea();
        aboutText.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        aboutText.setBackground(Color.WHITE);
        aboutText.setForeground(Color.DARK_GRAY);
        aboutText.setEditable(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);

        aboutText.setText(
            "Dhan Vaanijya is a comprehensive software solution designed to streamline business operations\n"
          + "and empower users with powerful financial tools. Our mission is to provide intelligent, modern, and\n"
          + "user-friendly applications that make stock prediction and portfolio management easier than ever.\n\n"
          + "   Key Features:\n"
          + "   • User-friendly graphical interface\n"
          + "   • Real-time stock prediction using ML backend\n"
          + "   • News sentiment analysis and stock impact estimation\n"
          + "   • Portfolio & Watchlist management\n\n"
          + "    Version: 1.2 \n"
          + "    Developed by: Ansh Vishwakarma (Team Dhan Vaanijya)\n\n"
          + "    Contact:\n"
          + "   • Phone: +91-6262644418\n"
          + "   • Email: vishwakarmaansh014@gmail.com\n\n"
          + "   Technologies Used:\n"
          + "   • Java (Swing UI)\n"
          + "   • Python (Backend ML)\n"
          + " For more information, visit our website"
        );

        JScrollPane scrollPane = new JScrollPane(aboutText);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(20, 30, 20, 30),
            BorderFactory.createLineBorder(new Color(180, 200, 230), 1, true)
        ));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        add(scrollPane, BorderLayout.CENTER);
    }
}
