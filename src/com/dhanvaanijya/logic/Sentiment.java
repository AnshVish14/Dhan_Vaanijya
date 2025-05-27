package com.dhanvaanijya.logic;
import javax.swing.*;
import java.awt.*;

public class Sentiment extends JPanel{
    public Sentiment(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel l = new JLabel();
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBackground(Color.GRAY);
        l.setText("SENTIMENT analysis");
        add(l,BorderLayout.CENTER);
    }
}
