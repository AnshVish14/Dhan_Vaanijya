package com.dhanvaanijya.ui;

import javax.swing.*;
import java.awt.*;

public class AboutUs extends JPanel {

    public AboutUs() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("About Dhan Vaanijya", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        JTextArea at = new JTextArea();

        at.setText(" Dhan Vaanijya is a comprehensive software solution designed to streamline business operations and enhance productivity. Our mission is to provide innovative tools that empower businesses to thrive in a competitive market.\n\n" +
                "Key Features:\n" +
                "- User-friendly interface\n" +
                "- Robust data management\n" +
                "- Real-time analytics\n" +
                "- Customizable workflows\n"+ 
                "Version : 1.0.0 \n"
        +"\n Developed by : ANSH VISHWKARMA(TEAM DHANVAANIJYA)\n"
        +"\n Contact Details :\n" 
        +"\n Contectno. : +91\n"
        +"\n Email : \n"
        +"\nLanguage Used : JAVA,Python,BAT,CPP \n"
        + "\nFor more information, visit our website or contact our support team.");
        add(at,BorderLayout.CENTER);

    }
}
