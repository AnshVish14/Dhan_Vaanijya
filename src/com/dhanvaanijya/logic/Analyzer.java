package com.dhanvaanijya.logic;
import java.awt.*;
import javax.swing.*;
public class Analyzer extends JPanel{
    public Analyzer(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel l = new JLabel();
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBackground(Color.GRAY);
        l.setText("Stock Analyzer");
        add(l,BorderLayout.CENTER); 
    }
}
