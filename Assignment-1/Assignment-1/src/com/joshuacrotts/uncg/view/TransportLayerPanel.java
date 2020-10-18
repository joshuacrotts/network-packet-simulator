//=============================================================================================//
// FILENAME :       TransportLayerPanel.java
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
// AUTHOR   :   Joshua Crotts        START DATE :    27 Aug. 2020
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

public class TransportLayerPanel {

  /**
   *
   * @param ball
   */
  public static void openTransportPanel(Ball ball) {
    // Get the color for outputting to the title.
    String c = (ball.getColor() == Color.RED) ? "Red" : ((ball.getColor() == Color.BLUE) ? "Blue" : "NULL");

    JOptionPane.showConfirmDialog(null, createPane(ball), c + " Ball Transport Layer", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
  }

  public static JScrollPane createPane(Ball ball) {
    JPanel parentPanel = new JPanel();
    parentPanel.setMaximumSize(new Dimension(300, 300));
    parentPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    int row = 0;

    /* Pseudoheader. */
    JLabel pseudoHeaderLabel = new JLabel("Pseudoheader Section:");
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.ipady = 25;
    parentPanel.add(pseudoHeaderLabel, gbc);

    // Source IP.
    row++;
    addNetworkViewComponent("Source IP", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPaddedLong(NetworkUtils.SOURCE_IP, 8), 1, row, GridBagConstraints.EAST, parentPanel);

    // Destination IP.
    row++;
    addNetworkViewComponent("Destination IP", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPaddedLong(NetworkUtils.DESTINATION_IP, 8), 1, row, GridBagConstraints.EAST, parentPanel);

    // All Zeroes.
    row++;
    addNetworkViewComponent("All Zeroes", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent("00", 1, row, GridBagConstraints.EAST, parentPanel);

    // Protocol.
    row++;
    addNetworkViewComponent("Protocol", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.PROTOCOL, 2), 1, row, GridBagConstraints.EAST, parentPanel);

    row++;
    JLabel headerLabel = new JLabel("Header Section:");
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.ipady = 25;
    parentPanel.add(headerLabel, gbc);

    /* Header. */
    // Source port.
    row++;
    addNetworkViewComponent("Source Port", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.SOURCE_PORT, 4), 1, row, GridBagConstraints.EAST, parentPanel);

    // Destination port.
    row++;
    addNetworkViewComponent("Destination Port", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.DESTINATION_PORT, 4), 1, row, GridBagConstraints.EAST, parentPanel);

    // Sequence and ack. numbers.
    int seq = ball.getColor() == Color.RED ? NetworkUtils.RED_SEQ_NO : NetworkUtils.BLUE_SEQ_NO;
    int ack = ball.getColor() == Color.RED ? NetworkUtils.RED_ACK_NO : NetworkUtils.BLUE_ACK_NO;
    row++;
    addNetworkViewComponent("Sequence Number", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(seq, 8), 1, row, GridBagConstraints.EAST, parentPanel);

    row++;
    addNetworkViewComponent("Acknowledgement Number", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(ack, 8), 1, row, GridBagConstraints.EAST, parentPanel);

    // Flags.
    row++;
    addNetworkViewComponent("Flags", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.FLAGS, 4), 1, row, GridBagConstraints.EAST, parentPanel);

    // Window Size.
    row++;
    addNetworkViewComponent("Window Size", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.WIN_SIZE, 4), 1, row, GridBagConstraints.EAST, parentPanel);

    // Checksum.
    int checksum = ball.getColor() == Color.RED ? NetworkUtils.RED_CHECKSUM : NetworkUtils.BLUE_CHECKSUM;
    row++;
    addNetworkViewComponent("Checksum", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(checksum, 4), 1, row, GridBagConstraints.EAST, parentPanel);

    // Urgent pointer.
    row++;
    addNetworkViewComponent("Urgent Pointer", 0, row, GridBagConstraints.WEST, parentPanel);
    addNetworkViewComponent(NetworkUtils.toHexStrPadded(NetworkUtils.URG_PTR, 4), 1, row, GridBagConstraints.EAST, parentPanel);

    row++;
    JLabel dataLabel = new JLabel("Data Section:");
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.ipady = 25;
    parentPanel.add(dataLabel, gbc);
    
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
