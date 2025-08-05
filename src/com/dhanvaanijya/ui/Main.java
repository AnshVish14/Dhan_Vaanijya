package com.dhanvaanijya.ui;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static volatile boolean splashRunning = true;

    public static void main(String[] args) {
        System.out.println("WELCOME TO DHANVAANIJYA");
        System.out.println("'' HAR HAR MAHADEV ' Starting DHAN VAANIJYA...");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("MenuBar.background", Color.LIGHT_GRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showSplashScreen(4000); // Show animated splash screen

        SwingUtilities.invokeLater(() -> new loginregister().setVisible(true));
    }

    private static void showSplashScreen(int durationMs) {
        JWindow splash = new JWindow();

        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(20, 40, 80),
                        getWidth(), getHeight(), new Color(60, 90, 160));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };

        content.setLayout(new BorderLayout());

        // Title label
        JLabel title = new JLabel("", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Subtitle
        JLabel subTitle = new JLabel("AI-Powered Stock Market Assistant", SwingConstants.CENTER);
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subTitle.setForeground(Color.LIGHT_GRAY);

        // Quote with animated dots
        JLabel quote = new JLabel("Loading", SwingConstants.CENTER);
        quote.setFont(new Font("Monospaced", Font.BOLD, 14));
        quote.setForeground(new Color(200, 220, 255));
        quote.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Progress bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(400, 12));
        progressBar.setBorderPainted(false);
        progressBar.setForeground(new Color(0, 220, 220));
        progressBar.setBackground(Color.DARK_GRAY);

        // Center panel
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        centerPanel.add(title);
        centerPanel.add(subTitle);
        centerPanel.add(quote);
        centerPanel.add(progressBar);

        content.add(centerPanel, BorderLayout.CENTER);
        content.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 250), 2));

        splash.getContentPane().add(content);
        splash.setSize(520, 300);
        splash.setLocationRelativeTo(null);
        splash.setOpacity(0f);
        splash.setVisible(true);

        // Fade in
        for (float i = 0f; i <= 1.0f; i += 0.05f) {
            splash.setOpacity(i);
            try { Thread.sleep(20); } catch (Exception e) {}
        }

        // Start animated dots in separate thread
        Thread dotThread = new Thread(() -> animatedDots(quote));
        splashRunning = true;
        dotThread.start();

        // Typewriter effect — blocking
        typewriterEffect(title, "DHAN VAANIJYA", 90);

        // Keep splash open for specified duration
        try {
            Thread.sleep(durationMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splashRunning = false; // stop dots animation

        // Fade out
        for (float i = 1.0f; i >= 0f; i -= 0.05f) {
            splash.setOpacity(i);
            try { Thread.sleep(20); } catch (Exception e) {}
        }

        splash.dispose();
    }

    // Typewriter effect
    public static void typewriterEffect(JLabel label, String text, int delayMs) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(c);
            label.setText(sb.toString());
            try { Thread.sleep(delayMs); } catch (Exception e) {}
        }
    }

    // Animated dots
    public static void animatedDots(JLabel label) {
        String base = "“Har Har Mahadev”";
        while (splashRunning) {
            for (int i = 0; i <= 3; i++) {
                if (!splashRunning) return;
                label.setText(base + ".".repeat(i));
                try { Thread.sleep(400); } catch (Exception e) {}
            }
        }
    }
}
