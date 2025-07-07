package com.dhanvaanijya.data;

import javax.swing.*;
import java.awt.*;

public class StockWatchlist extends JPanel{
    public StockWatchlist(){
         setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel l = new JLabel();
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBackground(Color.GRAY);
        l.setText(" stock saved watch list comming in next update ");
        add(l,BorderLayout.CENTER); 
    }
}
