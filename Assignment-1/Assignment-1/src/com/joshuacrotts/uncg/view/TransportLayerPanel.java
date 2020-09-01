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
import javax.swing.JOptionPane;

public class TransportLayerPanel {

  public static void openTransportPanel(Ball ball) {
    if (ball.getColor() == Color.RED) {
      String msg = "PSEUDOHEADER:"
              + "\n\nSource IP: " + Integer.toHexString(NetworkUtils.SOURCE_IP)
              + "\nDestination IP: " + Integer.toHexString(NetworkUtils.DESTINATION_IP)
              + "\nAll Zeroes: " + "0000"
              + "\nProtocol: " + Integer.toHexString(NetworkUtils.PROTOCOL)
              + "\nTCP Length (Hex): " + Integer.toHexString(ball.getNetworkData().tcpLength)
              + "\n\n"
              + "HEADER:"
              + "\nSource Port: " + Integer.toHexString(NetworkUtils.SOURCE_PORT)
              + "\nDestination Port: " + Integer.toHexString(NetworkUtils.DESTINATION_PORT)
              + "\nSequence Number: " + Integer.toHexString(NetworkUtils.RED_SEQ_NO)
              + "\nAcknowledgement Number: " + Integer.toHexString(NetworkUtils.RED_ACK_NO)
              + "\nFlags: " + Integer.toHexString(NetworkUtils.FLAGS)
              + "\nWindow Size (Hex): " + Integer.toHexString(NetworkUtils.WIN_SIZE)
              + "\nChecksum: " + Integer.toHexString(NetworkUtils.RED_CHECKSUM)
              + "\nUrgent Ptr: " + Integer.toHexString(NetworkUtils.URG_PTR)
              + "\n\nDATA:"
              + "\n" + ball.getNetworkData().message;

      JOptionPane.showMessageDialog(null, msg, "Red Ball Transport Layer", JOptionPane.INFORMATION_MESSAGE);
    } else {
      String msg = "PSEUDOHEADER: "
              + "\n\nSource IP: " + Integer.toHexString(NetworkUtils.SOURCE_IP)
              + "\nDestination IP: " + Integer.toHexString(NetworkUtils.DESTINATION_IP)
              + "\nAll Zeroes: " + "0000"
              + "\nProtocol: " + Integer.toHexString(NetworkUtils.PROTOCOL)
              + "\nTCP Length (Hex): " + Integer.toHexString(ball.getNetworkData().tcpLength)
              + "\n\n"
              + "HEADER:"
              + "\nSource Port: " + Integer.toHexString(NetworkUtils.SOURCE_PORT)
              + "\nDestination Port: " + Integer.toHexString(NetworkUtils.DESTINATION_PORT)
              + "\nSequence Number: " + Integer.toHexString(NetworkUtils.BLUE_SEQ_NO)
              + "\nAcknowledgement Number: " + Integer.toHexString(NetworkUtils.BLUE_ACK_NO)
              + "\nFlags: " + Integer.toHexString(NetworkUtils.FLAGS)
              + "\nWindow Size (Hex): " + Integer.toHexString(NetworkUtils.WIN_SIZE)
              + "\nChecksum: " + Integer.toHexString(NetworkUtils.BLUE_CHECKSUM)
              + "\nUrgent Ptr: " + Integer.toHexString(NetworkUtils.URG_PTR)
              + "\n\nDATA:"
              + "\n" + ball.getNetworkData().message;
      
      JOptionPane.showMessageDialog(null, msg, "Blue Ball Transport Layer", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
