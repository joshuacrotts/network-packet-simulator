package com.joshuacrotts.uncg.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author joshuacrotts
 */
public class NetworkView extends JPanel {

    private final JFrame networkFrame;

    public NetworkView(int width, int height, String title) {
        this.networkFrame = new JFrame(title);
        this.networkFrame.setMaximumSize(new Dimension(width, height));
        this.networkFrame.setMinimumSize(new Dimension(width, height));
        this.networkFrame.setPreferredSize(new Dimension(width, height));
        this.networkFrame.getContentPane().add(this);
        this.networkFrame.setLocationRelativeTo(null);
        this.networkFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.networkFrame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        this.setBackground(Color.RED);
    }
}
