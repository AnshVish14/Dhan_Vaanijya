package com.dhanvaanijya.ui;

import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class backendconnector {

    public static String sendstockrequest(String stock, double pastPrice, double currentPrice, String news) {
        try {
            URI uri = new URI("http://127.0.0.1:8000/predict");
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Create JSON payload
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("stock", stock);
            jsonInput.put("past_price", pastPrice);
            jsonInput.put("current_price", currentPrice);
            jsonInput.put("news", news);

            // Send JSON input
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read response
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Build readable output string
            StringBuilder result = new StringBuilder();
            result.append("Stock: ").append(jsonResponse.getString("stock")).append("\n");
            result.append("Past Price: ").append(jsonResponse.getDouble("past_price")).append("\n");
            result.append("Current Price: ").append(jsonResponse.getDouble("current_price")).append("\n");
            result.append("Percent Change: ").append(jsonResponse.getDouble("percent_change")).append("%\n");
            result.append("Price Prediction: ").append(jsonResponse.getString("price_prediction")).append("\n");
            result.append("News Sentiment: ").append(jsonResponse.getString("news_sentiment")).append("\n");
            result.append("Sentiment Scores: ").append(jsonResponse.getJSONObject("sentiment_scores").toString()).append("\n");

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
