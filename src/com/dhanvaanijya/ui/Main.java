package com.dhanvaanijya.ui;

import javax.swing.UIManager;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME TO DHANVAANIJYA");
        System.out.println("'' HAR HAR MAHADEV ' Starting DHAN VAANIJYA...");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("MenuBar.background", Color.LIGHT_GRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }
}
