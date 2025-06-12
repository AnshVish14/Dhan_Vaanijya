package com.dhanvaanijya.logic;

import javax.swing.*;
import java.awt.*;

import com.dhanvaanijya.ui.backendconnector; // import your backend connector

public class Stockpredict extends JPanel {
    private JTextField stockInput;
    private JButton predictBtn;
    private JTextArea resultArea;

    public Stockpredict() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Stock Prediction");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        stockInput = new JTextField(15);
        predictBtn = new JButton("Predict");

        inputPanel.add(new JLabel("Stock Symbol:"));
        inputPanel.add(stockInput);
        inputPanel.add(predictBtn);

        add(inputPanel, BorderLayout.CENTER);

        // Result area
        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        // Button action: call backendconnector.sendstockrequest
        predictBtn.addActionListener(e -> {
            String stock = stockInput.getText().trim();
            if (stock.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a stock symbol");
                return;
            }

            // Run in a background thread to avoid freezing UI
            new Thread(() -> {
                String response = backendconnector.sendstockrequest(stock);
                SwingUtilities.invokeLater(() -> resultArea.setText(response));
            }).start();
        });
    }
}
