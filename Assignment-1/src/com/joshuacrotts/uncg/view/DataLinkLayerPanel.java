//=============================================================================================//
// FILENAME :       DataLinkLayerPanel.java
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
// AUTHOR   :   Joshua Crotts        START DATE :    10 Nov. 2020
// CLASS    :   CSC - 677 
// SEMESTER :   FALL 2020
//
//=============================================================================================//
package com.joshuacrotts.uncg.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.joshuacrotts.uncg.NetworkUtils;
import com.joshuacrotts.uncg.model.Ball;

public class DataLinkLayerPanel {

  /**
   *
   * @param ball
   */
  public static void openDataLinkPanel(Ball ball) {
    // Get the color for outputting to the title.
    String c = (ball.getColor() == Color.RED) ? "Red" : ((ball.getColor() == Color.BLUE) ? "Blue" : "NULL");

    JOptionPane.showConfirmDialog(null, createPane(ball), c + " Ball Data Link Layer", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * 
   * @param ball
   * @return
   */
  public static JScrollPane createPane(Ball ball) {
    JPanel parentPanel = new JPanel();
    parentPanel.setMaximumSize(new Dimension(300, 300));
    parentPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    int row = 0;
    
    /* Pseudoheader. */
    JLabel ipHeaderLabel = new JLabel("Ethernet Header Section:");
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.ipady = 25;
    parentPanel.add(ipHeaderLabel, gbc);

    // Preamble.
    row++;
    addDataLinkViewComponent("Preamble", 0, row, GridBagConstraints.WEST, parentPanel);
    addDataLinkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.PREAMBLE, 14), 1, row, GridBagConstraints.EAST, parentPanel);
    
    // SFD.
    row++;
    addDataLinkViewComponent("SFD", 0, row, GridBagConstraints.WEST, parentPanel);
    addDataLinkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.SFD, 2), 1, row, GridBagConstraints.EAST, parentPanel);
    
    // Destination Mac.
    row++;
    String destMacFormatted = NetworkUtils.convertMacToHexStr(NetworkUtils.DESTINATION_MAC);
    addDataLinkViewComponent("Destination MAC", 0, row, GridBagConstraints.WEST, parentPanel);
    addDataLinkViewComponent(destMacFormatted, 1, row, GridBagConstraints.EAST, parentPanel);
    
    // Ethernet Mac.
    row++;
    String srcMacFormatted = NetworkUtils.convertMacToHexStr(NetworkUtils.SOURCE_MAC);
    addDataLinkViewComponent("Source MAC", 0, row, GridBagConstraints.WEST, parentPanel);
    addDataLinkViewComponent(srcMacFormatted, 1, row, GridBagConstraints.EAST, parentPanel);
    
    // SFD.
    row++;
    addDataLinkViewComponent("Type", 0, row, GridBagConstraints.WEST, parentPanel);
    addDataLinkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.ETHERNET_TYPE, 4), 1, row, GridBagConstraints.EAST, parentPanel);
    
    row++;
    JLabel headerLabel = new JLabel("Payload:");
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.ipady = 25;
    parentPanel.add(headerLabel, gbc);
    
    // Data.
    row++;
    addDataLinkViewComponent("Data", 0, row, GridBagConstraints.WEST, parentPanel);
    addDataLinkViewComponent(ball.getNetworkData().ipDatagram, 1, row, GridBagConstraints.EAST, parentPanel);
   
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
  private static void addDataLinkViewComponent(String text, int gridx, int gridy, int anchor, JPanel panel) {
    JLabel label = new JLabel(text);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = anchor;
    gbc.weightx = 0.5;
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    panel.add(label, gbc);
  }
}
