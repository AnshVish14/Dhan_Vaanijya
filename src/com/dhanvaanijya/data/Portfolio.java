package com.dhanvaanijya.data;

import javax.swing.*;
import java.awt.*;

public class Portfolio extends JPanel{
    public Portfolio(){
         setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel l = new JLabel();
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBackground(Color.GRAY);
        l.setText(" Portfolio Management ");
        add(l,BorderLayout.CENTER); 
    }
}
