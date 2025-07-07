package com.dhanvaanijya.ui;
import javax.swing.*;
import java.awt.*;
public class Setting extends JPanel {
    public Setting(){
        setBackground(Color.WHITE);
        JLabel l = new JLabel();
        l.setFont(new Font("Arial",Font.BOLD,20));
        l.setBackground(Color.WHITE);
        l.setText("Setting PANEL comming in next update");
        add(l, BorderLayout.NORTH);
    }
}
