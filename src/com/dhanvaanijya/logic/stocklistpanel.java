package com.dhanvaanijya.logic;
import com.dhanvaanijya.logic.Stockpredict;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

public class stocklistpanel extends JPanel {
    private static final String API_KEY = "d2e5uqpr01qjrul67aggd2e5uqpr01qjrul67ah0";
    private static final String API_URL = "https://finnhub.io/api/v1/stock/symbol?exchange=US&token=" + API_KEY;

    private DefaultListModel<String> stockModel = new DefaultListModel<>();
    private java.util.List<String> fullStockList = new ArrayList<>();
    private java.util.List<String> lastSuccessfulStockList = new ArrayList<>();
    private JList<String> stockList;
    private JTextArea detailArea;
    private JLabel loadingLabel;
    private JTextField searchField;
    private JButton predictButton;

    public stocklistpanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JLabel header = new JLabel("LIVE Stock List", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(new Color(30, 60, 110));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        add(header, BorderLayout.NORTH);

        // Search field
        searchField = new JTextField();
        searchField.setFont(new Font("Consolas", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 144, 255), 2, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        searchField.setToolTipText("Search stock by symbol or name...");

        // Stock list
        stockList = new JList<>(stockModel);
        stockList.setFont(new Font("Consolas", Font.PLAIN, 14));
        stockList.setSelectionBackground(new Color(100, 149, 237));
        stockList.setSelectionForeground(Color.WHITE);
        stockList.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
        JScrollPane scrollPane = new JScrollPane(stockList);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchField, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Detail area
        detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        detailArea.setBackground(new Color(240, 245, 255));
        detailArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100, 120, 180), 2), "Stock Details"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane detailScroll = new JScrollPane(detailArea);
        detailScroll.setPreferredSize(new Dimension(350, 220));
        add(detailScroll, BorderLayout.SOUTH);

        // Loading label
        loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        loadingLabel.setVisible(false);
        add(loadingLabel, BorderLayout.EAST);

        // Top panel for button with header
        JPanel topButtonPanel = new JPanel(new BorderLayout());
        topButtonPanel.setOpaque(true);
        topButtonPanel.setBackground(new Color(173, 216, 230)); // light blue header background

        JLabel headerLabel = new JLabel("Stock Prediction", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setBackground(new Color(30, 60, 120));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topButtonPanel.add(headerLabel, BorderLayout.NORTH);
        

        // Button panel aligned to the right
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        btnPanel.setOpaque(false);

        predictButton = new JButton("Stock Predict");
        predictButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        predictButton.setForeground(Color.BLACK); // text color
        predictButton.setBackground(new Color(173, 216, 230)); // light blue
        predictButton.setFocusPainted(false);
        predictButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        predictButton.setOpaque(true);

        // Animation effect on hover
        predictButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                predictButton.setBackground(new Color(135, 206, 250)); // hover color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                predictButton.setBackground(new Color(173, 216, 230)); // original color
            }
        });

        btnPanel.add(predictButton);
        topButtonPanel.add(btnPanel, BorderLayout.SOUTH);

        add(topButtonPanel, BorderLayout.NORTH);

        // Fetch stocks
        fetchStocks();

        // Stock selection listener
        stockList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = stockList.getSelectedValue();
                if (selected != null && !selected.contains("Failed") && !selected.contains("Network timeout")) {
                    String symbol = selected.split(" - ")[0].trim();
                    fetchStockDetails(symbol);
                }
            }
        });

        // Search filter
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filterList() {
                String query = searchField.getText().toLowerCase();
                stockModel.clear();
                for (String item : fullStockList) {
                    if (item.toLowerCase().contains(query)) stockModel.addElement(item);
                }
            }
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
        });

        // Stock Predict action
        predictButton.addActionListener(e -> {
            String selected = stockList.getSelectedValue();
            if (selected == null || selected.contains("Failed") || selected.contains("Network timeout")) {
                JOptionPane.showMessageDialog(this, "Please select a valid stock first.", "No Stock Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String symbol = selected.split(" - ")[0].trim();
            String details = detailArea.getText();

            JFrame predictFrame = new JFrame("Stock Prediction for " + symbol);
            Stockpredict spPanel = new Stockpredict();
            spPanel.prefillFields(symbol, details);

            predictFrame.setContentPane(spPanel);
            predictFrame.setSize(900, 500);
            predictFrame.setLocationRelativeTo(null);
            predictFrame.setVisible(true);
        });
    }

    private void fetchStocks() {
        loadingLabel.setVisible(true);
        stockModel.clear();
        fullStockList.clear();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                int attempts = 0;
                while (attempts < 3) {
                    try {
                        HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(10000);
                        conn.setReadTimeout(10000);

                        int status = conn.getResponseCode();
                        if (status != 200) throw new IOException("HTTP Response code: " + status);

                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) response.append(line);
                        in.close();

                        JSONArray stocks = new JSONArray(response.toString());
                        for (int i = 0; i < Math.min(stocks.length(), 200); i++) {
                            JSONObject stock = stocks.getJSONObject(i);
                            String symbol = stock.getString("symbol");
                            String desc = stock.getString("description");
                            String display = symbol + " - " + desc;
                            fullStockList.add(display);
                            stockModel.addElement(display);
                        }
                        lastSuccessfulStockList.clear();
                        lastSuccessfulStockList.addAll(fullStockList);
                        return null; // success
                    } catch (SocketTimeoutException e) {
                        attempts++;
                        if (attempts < 3) try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                    } catch (Exception e) {
                        attempts = 3;
                        stockModel.addElement("Failed to fetch stock data: " + e.getMessage());
                    }
                }
                if (!lastSuccessfulStockList.isEmpty()) {
                    stockModel.clear();
                    for (String s : lastSuccessfulStockList) stockModel.addElement(s);
                    stockModel.addElement("Displayed last cached data due to network issue.");
                } else {
                    stockModel.addElement("Network timeout. Please check your connection.");
                }
                return null;
            }

            @Override
            protected void done() { loadingLabel.setVisible(false); }
        };
        worker.execute();
    }

    private void fetchStockDetails(String symbol) {
        detailArea.setText("Loading details for " + symbol + "...");
        loadingLabel.setVisible(true);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            String details = "";

            @Override
            protected Void doInBackground() {
                int attempts = 0;
                while (attempts < 3) {
                    try {
                        URL quoteUrl = new URL("https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + API_KEY);
                        HttpURLConnection quoteConn = (HttpURLConnection) quoteUrl.openConnection();
                        quoteConn.setRequestMethod("GET");
                        quoteConn.setConnectTimeout(10000);
                        quoteConn.setReadTimeout(10000);

                        BufferedReader qIn = new BufferedReader(new InputStreamReader(quoteConn.getInputStream()));
                        StringBuilder qRes = new StringBuilder();
                        String line;
                        while ((line = qIn.readLine()) != null) qRes.append(line);
                        qIn.close();

                        JSONObject qJson = new JSONObject(qRes.toString());
                        double current = qJson.optDouble("c", -1);
                        double prev = qJson.optDouble("pc", -1);

                        URL newsUrl = new URL("https://finnhub.io/api/v1/company-news?symbol=" + symbol +
                                "&from=" + java.time.LocalDate.now().minusDays(7) +
                                "&to=" + java.time.LocalDate.now() +
                                "&token=" + API_KEY);
                        HttpURLConnection newsConn = (HttpURLConnection) newsUrl.openConnection();
                        newsConn.setRequestMethod("GET");
                        newsConn.setConnectTimeout(10000);
                        newsConn.setReadTimeout(10000);

                        BufferedReader nIn = new BufferedReader(new InputStreamReader(newsConn.getInputStream()));
                        StringBuilder nRes = new StringBuilder();
                        while ((line = nIn.readLine()) != null) nRes.append(line);
                        nIn.close();

                        JSONArray newsArr = new JSONArray(nRes.toString());
                        StringBuilder newsText = new StringBuilder();
                        if (newsArr.length() == 0) newsText.append("No recent news available.");
                        else for (int i = 0; i < Math.min(newsArr.length(), 5); i++)
                            newsText.append("• ").append(newsArr.getJSONObject(i).optString("headline")).append("\n");

                        details = "Symbol: " + symbol + "\nPrevious Close: " + prev + "\nCurrent Price: " + current +
                                "\n\nRecent News:\n" + newsText.toString();
                        return null; // success
                    } catch (SocketTimeoutException e) {
                        attempts++;
                        if (attempts < 3) try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                    } catch (Exception e) {
                        attempts = 3;
                        details = "Failed to fetch details: " + e.getMessage();
                    }
                }
                if (details.isEmpty()) details = "Network timeout. Please check your connection.";
                return null;
            }

            @Override
            protected void done() {
                loadingLabel.setVisible(false);
                detailArea.setText(details);
            }
        };
        worker.execute();
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
