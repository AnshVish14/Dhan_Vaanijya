package com.dhanvaanijya.logic;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import com.dhanvaanijya.ui.backendconnector;

public class Stockpredict extends JPanel {
    private JTextField stockInput, pastPriceInput, currentPriceInput;
    private JTextArea newsInput;
    private JButton predictBtn, clearBtn;
    private JTextArea resultArea;

    public Stockpredict() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 247, 250));  // Light background
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Stock Prediction", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 100));
        add(titleLabel, BorderLayout.NORTH);

        // Main content split horizontally: input form on left, result on right
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(null);
        splitPane.setBackground(getBackground());

        // Left panel - form inputs and buttons
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(220, 230, 250), 0, getHeight(), new Color(245, 250, 255));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Row 0 - Stock Symbol
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel stockLabel = new JLabel("Stock Symbol:");
        stockLabel.setFont(labelFont);
        stockLabel.setForeground(new Color(30, 60, 120));
        formPanel.add(stockLabel, gbc);

        gbc.gridx = 1;
        stockInput = new JTextField();
        styleTextField(stockInput, inputFont);
        formPanel.add(stockInput, gbc);

        // Row 1 - Past Price
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel pastPriceLabel = new JLabel("Past Price:");
        pastPriceLabel.setFont(labelFont);
        pastPriceLabel.setForeground(new Color(30, 60, 120));
        formPanel.add(pastPriceLabel, gbc);

        gbc.gridx = 1;
        pastPriceInput = new JTextField();
        styleTextField(pastPriceInput, inputFont);
        formPanel.add(pastPriceInput, gbc);

        // Row 2 - Current Price
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel currentPriceLabel = new JLabel("Current Price:");
        currentPriceLabel.setFont(labelFont);
        currentPriceLabel.setForeground(new Color(30, 60, 120));
        formPanel.add(currentPriceLabel, gbc);

        gbc.gridx = 1;
        currentPriceInput = new JTextField();
        styleTextField(currentPriceInput, inputFont);
        formPanel.add(currentPriceInput, gbc);

        // Row 3 - News (textarea inside JScrollPane)
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel newsLabel = new JLabel("News:");
        newsLabel.setFont(labelFont);
        newsLabel.setForeground(new Color(30, 60, 120));
        formPanel.add(newsLabel, gbc);

        gbc.gridx = 1;
        newsInput = new JTextArea(5, 20);
        styleTextArea(newsInput, inputFont);
        JScrollPane newsScroll = new JScrollPane(newsInput);
        newsScroll.setBorder(new LineBorder(new Color(200, 210, 230), 1, true));
        newsScroll.getVerticalScrollBar().setUnitIncrement(10);
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(newsScroll, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 4 - Buttons panel (Predict + Clear)
        gbc.gridx = 0; gbc.gridy = 4; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        predictBtn = new JButton("Predict");
        styleButton(predictBtn);
        buttonPanel.add(predictBtn);

        clearBtn = new JButton("Clear");
        styleButton(clearBtn);
        clearBtn.setBackground(new Color(180, 180, 180)); // lighter gray for clear button
        clearBtn.setForeground(Color.BLACK);
        clearBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                clearBtn.setBackground(new Color(150, 150, 150));
            }
            public void mouseExited(MouseEvent e) {
                clearBtn.setBackground(new Color(180, 180, 180));
            }
        });
        buttonPanel.add(clearBtn);

        formPanel.add(buttonPanel, gbc);

        // Add form panel to split pane
        splitPane.setLeftComponent(formPanel);

        // Right panel - result area with scroll
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100, 120, 180), 2), "Prediction Result"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(null);
        splitPane.setRightComponent(resultScroll);

        add(splitPane, BorderLayout.CENTER);

        // Button action listener for Predict
        predictBtn.addActionListener(e -> {
            String stock = stockInput.getText().trim();
            String pastStr = pastPriceInput.getText().trim();
            String currentStr = currentPriceInput.getText().trim();
            String news = newsInput.getText().trim();

            if (stock.isEmpty() || pastStr.isEmpty() || currentStr.isEmpty() || news.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double pastPrice, currentPrice;
            try {
                pastPrice = Double.parseDouble(pastStr);
                currentPrice = Double.parseDouble(currentStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for prices.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            new Thread(() -> {
                String response = backendconnector.sendstockrequest(stock, pastPrice, currentPrice, news);
                SwingUtilities.invokeLater(() -> resultArea.setText(response));
            }).start();
        });

        // Button action listener for Clear
        clearBtn.addActionListener(e -> {
            stockInput.setText("");
            pastPriceInput.setText("");
            currentPriceInput.setText("");
            newsInput.setText("");
            resultArea.setText("");
        });
    }

    private void styleTextField(JTextField field, Font font) {
        field.setFont(font);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 170, 220), 2, true),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        field.setCaretColor(new Color(50, 80, 150));
    }

    private void styleTextArea(JTextArea area, Font font) {
        area.setFont(font);
        area.setBackground(Color.WHITE);
        area.setBorder(null);
        area.setCaretColor(new Color(50, 80, 150));
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(45, 90, 190));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(65, 120, 240));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(45, 90, 190));
            }
        });
    }
}
