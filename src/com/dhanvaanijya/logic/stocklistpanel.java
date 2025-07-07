package com.dhanvaanijya.logic;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.json.JSONArray;

public class stocklistpanel extends JPanel {
    private static final String API_KEY = "d18na0hr01qt6geqar8gd18na0hr01qt6geqar90"; // Replace with your key
    private static final String API_URL = "https://finnhub.io/api/v1/stock/symbol?exchange=US&token=" + API_KEY;

    private DefaultListModel<String> stockmodel = new DefaultListModel<>();
    private JList<String> stock;
    private JTextArea detailArea;
    private JLabel loadingLabel;

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

        // Stock List with custom renderer for better look
        stock = new JList<>(stockmodel);
        stock.setFont(new Font("Consolas", Font.PLAIN, 14));
        stock.setForeground(new Color(25, 25, 112));
        stock.setBackground(Color.WHITE);
        stock.setSelectionBackground(new Color(100, 149, 237)); // Cornflower blue
        stock.setSelectionForeground(Color.WHITE);
        stock.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
        stock.setFixedCellHeight(28);
        stock.setCellRenderer(new StockListCellRenderer());

        JScrollPane scrollPane = new JScrollPane(stock);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Detail area with background and padding
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
        detailScroll.setBorder(BorderFactory.createEmptyBorder());
        add(detailScroll, BorderLayout.SOUTH);

        // Loading label (hidden by default)
        loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        loadingLabel.setForeground(new Color(80, 80, 80));
        loadingLabel.setVisible(false);
        add(loadingLabel, BorderLayout.EAST);

        fetchStocks();

        stock.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = stock.getSelectedValue();
                if (selected != null && !selected.equals("Failed to fetch stock data.")) {
                    String symbol = selected.split(" - ")[0].trim();
                    fetchStockDetails(symbol);
                }
            }
        });
    }

    private void fetchStocks() {
        loadingLabel.setVisible(true);
        stockmodel.clear();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    URI uri = URI.create(API_URL);
                    URL url = uri.toURL();
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONArray stocks = new JSONArray(response.toString());

                    for (int i = 0; i < Math.min(stocks.length(), 100); i++) {
                        JSONObject stock = stocks.getJSONObject(i);
                        String symbol = stock.getString("symbol");
                        String desc = stock.getString("description");
                        stockmodel.addElement(symbol + " - " + desc);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    stockmodel.addElement("Failed to fetch stock data.");
                }
                return null;
            }

            @Override
            protected void done() {
                loadingLabel.setVisible(false);
            }
        };
        worker.execute();
    }

    private void fetchStockDetails(String symbol) {
        detailArea.setText("Loading details for " + symbol + "...");
        loadingLabel.setVisible(true);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            String detailsText = "";

            @Override
            protected Void doInBackground() {
                try {
                    // 1. Quote API: current and past price
                    URI quoteUri = new URI("https", "finnhub.io", "/api/v1/quote", "symbol=" + symbol + "&token=" + API_KEY, null);
                    URL quoteUrl = quoteUri.toURL();
                    HttpURLConnection quoteConn = (HttpURLConnection) quoteUrl.openConnection();
                    quoteConn.setRequestMethod("GET");

                    BufferedReader quoteIn = new BufferedReader(new InputStreamReader(quoteConn.getInputStream()));
                    StringBuilder quoteResponse = new StringBuilder();
                    String quoteLine;
                    while ((quoteLine = quoteIn.readLine()) != null) {
                        quoteResponse.append(quoteLine);
                    }
                    quoteIn.close();

                    JSONObject quoteJson = new JSONObject(quoteResponse.toString());
                    double currentPrice = quoteJson.optDouble("c", -1);
                    double pastPrice = quoteJson.optDouble("pc", -1);

                    // 2. News API
                    LocalDate today = LocalDate.now();
                    LocalDate weekAgo = today.minusDays(7);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    URI newsUri = new URI(
                        "https",
                        "finnhub.io",
                        "/api/v1/company-news",
                        "symbol=" + symbol
                            + "&from=" + weekAgo.format(formatter)
                            + "&to=" + today.format(formatter)
                            + "&token=" + API_KEY,
                        null
                    );
                    URL newsUrl = newsUri.toURL();

                    HttpURLConnection newsConn = (HttpURLConnection) newsUrl.openConnection();
                    newsConn.setRequestMethod("GET");

                    BufferedReader newsIn = new BufferedReader(new InputStreamReader(newsConn.getInputStream()));
                    StringBuilder newsResponse = new StringBuilder();
                    String newsLine;
                    while ((newsLine = newsIn.readLine()) != null) {
                        newsResponse.append(newsLine);
                    }
                    newsIn.close();

                    JSONArray newsJson = new JSONArray(newsResponse.toString());
                    StringBuilder newsText = new StringBuilder();
                    for (int i = 0; i < Math.min(newsJson.length(), 5); i++) {
                        JSONObject article = newsJson.getJSONObject(i);
                        newsText.append("• ").append(article.optString("headline")).append("\n");
                    }

                    detailsText = "Symbol: " + symbol + "\n"
                            + "Past Price: " + (pastPrice == -1 ? "N/A" : pastPrice) + "\n"
                            + "Current Price: " + (currentPrice == -1 ? "N/A" : currentPrice) + "\n"
                            + "Recent News:\n" + (newsText.length() > 0 ? newsText.toString() : "No news found.");

                } catch (Exception e) {
                    e.printStackTrace();
                    detailsText = "Failed to fetch details for " + symbol;
                }
                return null;
            }

            @Override
            protected void done() {
                loadingLabel.setVisible(false);
                detailArea.setText(detailsText);
            }
        };
        worker.execute();
    }

    // Custom cell renderer for better stock list appearance
    static class StockListCellRenderer extends DefaultListCellRenderer {
        private static final Color EVEN_COLOR = new Color(230, 240, 255);
        private static final Color ODD_COLOR = Color.WHITE;
        private static final Color SELECT_BG = new Color(100, 149, 237);
        private static final Color SELECT_FG = Color.WHITE;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
            if (isSelected) {
                label.setBackground(SELECT_BG);
                label.setForeground(SELECT_FG);
            } else {
                label.setBackground((index % 2 == 0) ? EVEN_COLOR : ODD_COLOR);
                label.setForeground(new Color(25, 25, 112));
            }
            label.setFont(new Font("Consolas", Font.PLAIN, 14));
            return label;
        }
    }
}
