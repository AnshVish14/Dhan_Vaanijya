package com.dhanvaanijya.ui;

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME TO CHANVAANIJYA");
        System.out.println(" Starting DHAN VAANIJYA...");

        // Open the Dashboard
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }
}
