package com.dhanvaanijya.ui;

import java.io.*;
import java.net.*;

public class backendconnector {
    public static String sendstockrequest(String stockname) {
        try {
            URI uri = new URI("http://127.0.0.1:8000/predict");
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json"); // fixed typo here
            conn.setDoOutput(true);

            String jsonInputString = "{\"stock\": \"" + stockname + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            return response.toString();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
