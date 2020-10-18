//=============================================================================================//
// FILENAME :       NetworkLayerPanel.java
//
// DESCRIPTION :
//
//
// NOTES :
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.
//
// AUTHOR   :   Joshua Crotts        START DATE :    12 Oct. 2020
// CLASS    :   CSC - 677 
// SEMESTER :   FALL 2020
//
//=============================================================================================//
package com.joshuacrotts.uncg.view;

import com.joshuacrotts.uncg.NetworkUtils;
import com.joshuacrotts.uncg.model.Ball;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NetworkLayerPanel {

  /**
   *
   * @param ball
   */
  public static void openNetworkPanel(Ball ball) {
    // Get the color for outputting to the title.
    String c = (ball.getColor() == Color.RED) ? "Red" : ((ball.getColor() == Color.BLUE) ? "Blue" : "NULL");

    JOptionPane.showConfirmDialog(null, createPane(ball), c + " Ball Network Layer", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
  }

  public static JScrollPane createPane(Ball ball) {
    JPanel parentPanel = new JPanel();
    parentPanel.setMaximumSize(new Dimension(300, 300));
    parentPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    int row = 0;

    JScrollPane p = new JScrollPane(parentPanel);
    return p;
  }

  /**
   *
   * @param text
   * @param gridx
   * @param gridy
   * @param anchor
   * @param panel
   */
  private static void addNetworkViewComponent(String text, int gridx, int gridy, int anchor, JPanel panel) {
    JLabel label = new JLabel(text);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = anchor;
    gbc.weightx = 0.5;
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    panel.add(label, gbc);
  }
}
