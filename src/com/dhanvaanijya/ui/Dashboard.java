package com.dhanvaanijya.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.dhanvaanijya.data.StockWatchlist;
import com.dhanvaanijya.logic.Stockpredict;
import com.dhanvaanijya.logic.stocklistpanel;

public class Dashboard extends JFrame {

    String[] menu = {
        "HOME", "Stockpredict", "StockWatchlist", "stocklistpanel",
        "Setting", "About Us", "Exit"
    };

    private CardLayout CARD = new CardLayout();
    private JPanel mainPanel;

    public Dashboard() {
        setTitle("DHAN VAANIJYA - Stock Assistant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        initUI();
    }

    private void initUI() {
        // ----- Top Bar -----
        JPanel titleBar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(30, 144, 255), getWidth(), getHeight(), new Color(65, 105, 225)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        titleBar.setPreferredSize(new Dimension(getWidth(), 60));
        titleBar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JLabel title = new JLabel("💹 DHAN VAANIJYA");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        titleBar.add(title);

        // ----- Sidebar -----
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(240, 248, 255));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setLayout(new GridLayout(menu.length, 1, 5, 5));
        sidebar.setBorder(new EmptyBorder(15, 10, 10, 10));

        for (String label : menu) {
            JButton button = new JButton(label);
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            button.setForeground(new Color(30, 60, 120));
            button.setBackground(new Color(255, 255, 255));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 200, 255), 1, true),
                new EmptyBorder(10, 20, 10, 20)
            ));

            // Hover effect
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(200, 220, 255));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.WHITE);
                }
            });

            button.addActionListener(e -> {
                switch (label) {
                    case "HOME": CARD.show(mainPanel, "HOME"); break;
                    case "Stockpredict": CARD.show(mainPanel, "Stockpredict"); break;
                    case "StockWatchlist": CARD.show(mainPanel, "StockWatchlist"); break;
                    case "stocklistpanel": CARD.show(mainPanel, "stocklistpanel"); break;
                    case "Setting": CARD.show(mainPanel, "Setting"); break;
                    case "About Us": CARD.show(mainPanel, "AboutUs"); break;
                    case "Exit":
                        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit App", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, label + " - Coming Soon!");
                }
            });

            sidebar.add(button);
        }

        // ----- Main Panel -----
        mainPanel = new JPanel(CARD);
        mainPanel.setBackground(Color.WHITE);

        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("<html><center><h1>Welcome to <font color='#1e90ff'>DHAN VAANIJYA</font></h1><p>The prediction tool for the traders.</p></center></html>", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        homePanel.add(welcome, BorderLayout.CENTER);

        mainPanel.add(homePanel, "HOME");
        mainPanel.add(new AboutUs(), "AboutUs");
        mainPanel.add(new Setting(), "Setting");
        mainPanel.add(new Stockpredict(), "Stockpredict");
        mainPanel.add(new stocklistpanel(), "stocklistpanel");
        mainPanel.add(new StockWatchlist(), "StockWatchlist");

        // ----- Add Panels to Frame -----
        add(titleBar, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        CARD.show(mainPanel, "HOME");
    }
}
