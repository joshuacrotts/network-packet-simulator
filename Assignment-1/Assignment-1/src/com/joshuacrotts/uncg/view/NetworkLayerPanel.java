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
    
    /* Pseudoheader. */
    JLabel ipHeaderLabel = new JLabel("IP Header Section:");
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.ipady = 25;
    parentPanel.add(ipHeaderLabel, gbc);

    // Version.
    row++;
    addNetworkViewComponent("Version", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.VERSION, 1), 1, row, GridBagConstraints.EAST, parentPanel);
    
    // IHL.
    row++;
    addNetworkViewComponent("IHL", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.IHL, 1), 1, row, GridBagConstraints.EAST, parentPanel);
    
    // TOS.
    row++;
    addNetworkViewComponent("Type of Service", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent("00", 1, row, GridBagConstraints.EAST, parentPanel);
    
    // Total Length of IP datagram.
    int len = ball.getColor() == Color.RED ? NetworkUtils.RED_IP_LENGTH : NetworkUtils.BLUE_IP_LENGTH;
    row++;
    addNetworkViewComponent("Total Length", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(len, 4), 1, row, GridBagConstraints.EAST, parentPanel);
    
    // IP Identification.
    row++;
    addNetworkViewComponent("IP Identification", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.IP_IDENTIFICATION, 4), 1, row, GridBagConstraints.EAST, parentPanel);
    
    // Fragment Offset.
    row++;
    addNetworkViewComponent("Fragment", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.FRAGMENT, 4), 1, row, GridBagConstraints.EAST, parentPanel);
    
    // TTL.
    row++;
    addNetworkViewComponent("TTL", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.TTL, 2), 1, row, GridBagConstraints.EAST, parentPanel);
    
    // Protocol.
    row++;
    addNetworkViewComponent("Protocol", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.PROTOCOL, 2), 1, row, GridBagConstraints.EAST, parentPanel);
    
    // Checksum.
    int checksum = ball.getColor() == Color.RED ? NetworkUtils.RED_IP_CHECKSUM : NetworkUtils.BLUE_TRANSPORT_CHECKSUM;
    row++;
    addNetworkViewComponent("Checksum", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(checksum, 4), 1, row, GridBagConstraints.EAST, parentPanel);

    // Source IP.
    row++;
    addNetworkViewComponent("Source IP", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPaddedLong(NetworkUtils.SOURCE_IP, 8), 1, row, GridBagConstraints.EAST, parentPanel);

    // Destination IP.
    long destIP = ball.getColor() == Color.RED ? NetworkUtils.MIDDLE_DESTINATION_IP : NetworkUtils.DESTINATION_IP;
    row++;
    addNetworkViewComponent("Destination IP", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPaddedLong(destIP, 8), 1, row, GridBagConstraints.EAST, parentPanel);
    
    /* Data. */
    row++;
    addNetworkViewComponent("Data", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(ball.getNetworkData().message, 1, row, GridBagConstraints.EAST, parentPanel);
    
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
