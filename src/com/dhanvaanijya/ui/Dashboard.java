package com.dhanvaanijya.ui;

import java.awt.*;
import javax.swing.*;

import com.dhanvaanijya.data.Portfolio;
import com.dhanvaanijya.logic.Sentiment;
import com.dhanvaanijya.logic.Stockpredict;

public class Dashboard extends JFrame {

    // Sidebar menu items
    String[] menu = {
        "HOME", "Stockpredict", "Portfolio",
        "Sentiment", "Setting", "About Us", "Exit"
    };
    CardLayout CARD = new CardLayout();
    private JPanel mainPanel;

    public Dashboard() {
        setTitle("DHAN_VAANIJYA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 690);
        initUI();

        setVisible(true);
    }

    private void initUI() {
        // Top bar
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(30, 144, 255));
        titleBar.setPreferredSize(new Dimension(getWidth(), 60));
        titleBar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JLabel title = new JLabel("DHAN VAANIJYA");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.WHITE);
        titleBar.add(title);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(100, 150, 200));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setLayout(new GridLayout(menu.length, 1, 6, 6));

        mainPanel = new JPanel(CARD);
        mainPanel.setBackground(Color.WHITE);

        // Cards
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        homePanel.add(new JLabel("Welcome to DHAN VAANIJYA", SwingConstants.CENTER), BorderLayout.CENTER);

        mainPanel.add(homePanel, "HOME");
        mainPanel.add(new AboutUs(), "AboutUs");
        mainPanel.add(new Setting(), "Setting");
        mainPanel.add(new Sentiment(), "Sentiment");
        mainPanel.add(new Stockpredict(), "Stockpredict");
       
        mainPanel.add(new Portfolio(), "Portfolio");


        for (String label : menu) {
            JButton button = new JButton(label);
            button.setFocusPainted(false);
            button.setBackground(Color.WHITE);
            button.setFont(new Font("Times New Roman", Font.BOLD, 16));
            button.setForeground(Color.BLUE);

            button.addActionListener(e -> {
                switch (label) {
                    case "About Us":
                        CARD.show(mainPanel, "AboutUs");
                        break;
                    case "HOME":
                        CARD.show(mainPanel, "HOME");
                        break;
                    case "Setting":
                        CARD.show(mainPanel, "Setting");
                        break;
                    case "Sentiment":
                        CARD.show(mainPanel, "Sentiment");
                        break;
                    case "Stockpredict":
                        CARD.show(mainPanel, "Stockpredict");
                        break;
                    case "Analyzer":
                        CARD.show(mainPanel, "Analyzer");
                        break;
                    case "Portfolio":
                        CARD.show(mainPanel, "Portfolio");
                        break;
                    case "Transactions":
                        CARD.show(mainPanel, "Transactions");
                        break;
                    case "Exit":
                        JOptionPane.showMessageDialog(this, "Exiting application");
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, label + " coming soon!");
                }
            });

            sidebar.add(button);
        }

        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        add(titleBar, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    // ✅ Custom Background Panel Class
    class BackgroundPanel extends JPanel {
        private Image bg;

        public BackgroundPanel(String imagePath) {
            bg = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
