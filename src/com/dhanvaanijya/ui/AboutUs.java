package com.dhanvaanijya.ui;
import javax.swing.*;
import java.awt.*;

public class AboutUs extends JPanel{
    
    public AboutUs(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel l = new JLabel();
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBackground(Color.GRAY);
        l.setText("ABOUTUS");
        add(l,BorderLayout.CENTER);
    } 
}
