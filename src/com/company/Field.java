package com.company;

import javax.swing.*;

public class Field extends JFrame{
    public Field(){
        this.add(new Game());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}
