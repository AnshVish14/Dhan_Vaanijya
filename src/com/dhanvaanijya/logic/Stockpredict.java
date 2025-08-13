package com.dhanvaanijya.logic;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import com.dhanvaanijya.ui.backendconnector;

public class Stockpredict extends JPanel {
    private JTextField stockInput, pastPriceInput, currentPriceInput;
    private JTextArea newsInput;
    private JButton predictBtn, clearBtn;
    private JTextArea resultArea;

    public Stockpredict() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title with background
        JLabel titleLabel = new JLabel("Stock Prediction", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(30, 60, 120)); // dark blue background
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titleLabel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(null);
        splitPane.setBackground(getBackground());

        // Left panel - Inputs
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Stock Symbol
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel stockLabel = new JLabel("Stock Symbol:");
        stockLabel.setFont(labelFont); stockLabel.setForeground(new Color(30, 60, 120));
        formPanel.add(stockLabel, gbc);
        gbc.gridx = 1; stockInput = new JTextField(); styleTextField(stockInput, inputFont); formPanel.add(stockInput, gbc);

        // Past Price
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel pastLabel = new JLabel("Past Price:"); pastLabel.setFont(labelFont); pastLabel.setForeground(new Color(30, 60, 120));
        formPanel.add(pastLabel, gbc);
        gbc.gridx = 1; pastPriceInput = new JTextField(); styleTextField(pastPriceInput, inputFont); formPanel.add(pastPriceInput, gbc);

        // Current Price
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel currentLabel = new JLabel("Current Price:"); currentLabel.setFont(labelFont); currentLabel.setForeground(new Color(30, 60, 120));
        formPanel.add(currentLabel, gbc);
        gbc.gridx = 1; currentPriceInput = new JTextField(); styleTextField(currentPriceInput, inputFont); formPanel.add(currentPriceInput, gbc);

        // News
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel newsLabel = new JLabel("News:"); newsLabel.setFont(labelFont); newsLabel.setForeground(new Color(30, 60, 120));
        formPanel.add(newsLabel, gbc);
        gbc.gridx = 1; newsInput = new JTextArea(5, 20); styleTextArea(newsInput, inputFont);
        JScrollPane newsScroll = new JScrollPane(newsInput);
        newsScroll.setBorder(new LineBorder(new Color(180, 200, 230), 1, true));
        newsScroll.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        gbc.fill = GridBagConstraints.BOTH; formPanel.add(newsScroll, gbc); gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buttons
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); 
        btnPanel.setOpaque(false);

        predictBtn = new JButton("Predict"); styleAnimatedButton(predictBtn, new Color(200, 200, 200));
        clearBtn = new JButton("Clear"); styleAnimatedButton(clearBtn, new Color(180,180,180));

        btnPanel.add(predictBtn); 
        btnPanel.add(clearBtn);
        formPanel.add(btnPanel, gbc);

        splitPane.setLeftComponent(formPanel);

        // Result panel
        resultArea = new JTextArea();
        resultArea.setEditable(false); resultArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        resultArea.setLineWrap(true); resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(250, 250, 255));
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100,120,180),2,true),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        splitPane.setRightComponent(resultScroll);

        add(splitPane, BorderLayout.CENTER);

        // Predict action
        predictBtn.addActionListener(e -> {
            String stock = stockInput.getText().trim();
            String pastStr = pastPriceInput.getText().trim();
            String currentStr = currentPriceInput.getText().trim();
            String news = newsInput.getText().trim();

            if(stock.isEmpty() || pastStr.isEmpty() || currentStr.isEmpty() || news.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Please fill all fields.","Input Error",JOptionPane.WARNING_MESSAGE); return;
            }
            double pastPrice, currentPrice;
            try { pastPrice = Double.parseDouble(pastStr); currentPrice = Double.parseDouble(currentStr); } 
            catch(NumberFormatException ex) { JOptionPane.showMessageDialog(this,"Please enter valid numbers for prices.","Input Error",JOptionPane.ERROR_MESSAGE); return; }

            new Thread(() -> {
                String response = backendconnector.sendstockrequest(stock,pastPrice,currentPrice,news);
                SwingUtilities.invokeLater(() -> resultArea.setText(response));
            }).start();
        });

        clearBtn.addActionListener(e -> {
            stockInput.setText(""); pastPriceInput.setText(""); currentPriceInput.setText(""); newsInput.setText(""); resultArea.setText("");
        });
    }

    private void styleTextField(JTextField field, Font font) {
        field.setFont(font); field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150,170,220),2,true),
                BorderFactory.createEmptyBorder(6,10,6,10)
        ));
        field.setCaretColor(new Color(50,80,150));
    }

    private void styleTextArea(JTextArea area, Font font) {
        area.setFont(font); area.setBackground(Color.WHITE);
        area.setBorder(null); area.setCaretColor(new Color(50,80,150));
    }

    private void styleAnimatedButton(JButton button, Color baseColor) {
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.BLACK);
        button.setBackground(baseColor);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(null);

        // Hover animation
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { 
                button.setBackground(baseColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) { 
                button.setBackground(baseColor);
            }
        });
    }

    // Prefill fields called from stocklistpanel
    public void prefillFields(String symbol, String details) {
        stockInput.setText(symbol);
        try {
            String[] lines = details.split("\n");
            for(String line : lines){
                if(line.startsWith("Previous Close:")) pastPriceInput.setText(line.split(":")[1].trim());
                else if(line.startsWith("Current Price:")) currentPriceInput.setText(line.split(":")[1].trim());
                else if(line.startsWith("Recent News:")) {
                    int idx = details.indexOf("Recent News:");
                    newsInput.setText(details.substring(idx+12).trim()); break;
                }
            }
        } catch(Exception e){}
    }

    // Custom Scrollbar UI
    private static class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override protected void configureScrollBarColors() { this.thumbColor = new Color(120,160,220); }
        @Override protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
        @Override protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
        private JButton createZeroButton() { JButton btn = new JButton(); btn.setPreferredSize(new Dimension(0,0)); btn.setMinimumSize(new Dimension(0,0)); btn.setMaximumSize(new Dimension(0,0)); return btn; }
    }
}
