package com.dhanvaanijya.ui;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("DHANVAANIJYA DASHBOARD");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initUI();
    }
    private void initUI(){
          JLabel welcomeLabel = new JLabel("🚀 Welcome to DHAN VAANIJYA", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton predictButton = new JButton("📊 Predict Stock");
        predictButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Prediction logic will go here!");
        });

        // Layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(predictButton, BorderLayout.CENTER);

        add(panel);
    }
}
