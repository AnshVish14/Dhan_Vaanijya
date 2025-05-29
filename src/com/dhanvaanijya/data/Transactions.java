package com.dhanvaanijya.data;

import javax.swing.*;
import java.awt.*;

public class Transactions extends JPanel{
    public Transactions(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel l = new JLabel();
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBackground(Color.GRAY);
        l.setText(" Transactuion Management ");
        add(l,BorderLayout.CENTER); 
    }
}
