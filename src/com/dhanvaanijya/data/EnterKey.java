package com.dhanvaanijya.data;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class EnterKey extends JPanel {
    private JTextField keyField;
    private JButton saveButton;

    public EnterKey() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Enter License Key:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        keyField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(keyField, gbc);

        saveButton = new JButton("Save Key");
        saveButton.addActionListener(e -> saveKey());
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(saveButton, gbc);
    }

    private void saveKey() {
        String key = keyField.getText().trim();
        if (key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Key cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (FileWriter writer = new FileWriter("license.key")) {
            writer.write(key);
            JOptionPane.showMessageDialog(this, "Key saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving key!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
