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
import static com.joshuacrotts.uncg.NetworkUtils.SOURCE_IP;
import com.joshuacrotts.uncg.model.Ball;
import java.awt.Color;
import javax.swing.JOptionPane;

public class TransportLayerPanel {

  public static void openTransportPanel(Ball ball) {
    String msg = "PSEUDOHEADER: "
            + "\n\nSource IP: " + NetworkUtils.toHexStringPadded(SOURCE_IP, 8)
            + "\nDestination IP: " + NetworkUtils.toHexStringPadded(NetworkUtils.DESTINATION_IP, 8)
            + "\nAll Zeroes: " + "00"
            + "\nProtocol: " + NetworkUtils.toHexStringPadded(NetworkUtils.PROTOCOL, 2)
            + "\nTCP Length (Hex): " + NetworkUtils.toHexStringPadded(ball.getNetworkData().tcpLength, 4)
            + "\n\n"
            + "HEADER:"
            + "\nSource Port: " + NetworkUtils.toHexStringPadded(NetworkUtils.SOURCE_PORT, 4)
            + "\nDestination Port: " + NetworkUtils.toHexStringPadded(NetworkUtils.DESTINATION_PORT, 4);

    // Append the sequence and acknowledgement number.
    if (ball.getColor() == Color.RED) {
      msg += "\nSequence Number: " + NetworkUtils.toHexStringPadded(NetworkUtils.RED_SEQ_NO, 8)
              + "\nAcknowledgement Number: " + NetworkUtils.toHexStringPadded(NetworkUtils.RED_ACK_NO, 8);
    } else {
      msg += "\nSequence Number: " + NetworkUtils.toHexStringPadded(NetworkUtils.BLUE_SEQ_NO, 8)
              + "\nAcknowledgement Number: " + NetworkUtils.toHexStringPadded(NetworkUtils.BLUE_ACK_NO, 8);
    }

    msg += "\nFlags: " + NetworkUtils.toHexStringPadded(NetworkUtils.FLAGS, 4)
            + "\nWindow Size (Hex): " + NetworkUtils.toHexStringPadded(NetworkUtils.WIN_SIZE, 4);

    // Append the checksum.
    if (ball.getColor() == Color.RED) {
      msg += "\nChecksum: " + NetworkUtils.toHexStringPadded(NetworkUtils.RED_CHECKSUM, 4);
    } else {
      msg += "\nChecksum: " + NetworkUtils.toHexStringPadded(NetworkUtils.BLUE_CHECKSUM, 4);
    }

    msg += "\nUrgent Ptr: " + NetworkUtils.toHexStringPadded(NetworkUtils.URG_PTR, 4)
            + "\n\nDATA:"
            + "\n" + ball.getNetworkData().message;

    JOptionPane.showMessageDialog(null, msg, "Red Ball Transport Layer", JOptionPane.INFORMATION_MESSAGE);
  }
}
