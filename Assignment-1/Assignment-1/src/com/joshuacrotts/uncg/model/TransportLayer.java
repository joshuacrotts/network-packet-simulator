//=============================================================================================//
// FILENAME :       TransportLayer.java
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
package com.joshuacrotts.uncg.model;

import com.joshuacrotts.uncg.NetworkData;
import com.joshuacrotts.uncg.NetworkUtils;
import java.awt.Color;

public class TransportLayer {

  /**
   * Each ball has its own respective network data.
   *
   * @param ball
   */
  public static void transport(Ball ball) {
    NetworkData data = ball.getNetworkData();

    /* First, convert the data into binary format. */
    String binaryMsg = NetworkUtils.convertASCIIToBinary(data.message);

    /* Now, pad the data to make sure it is a multiple of four in length. */
    String paddedMsg = binaryMsg;
    while (paddedMsg.length() % 16 != 0) {
      paddedMsg += "0";
    }

    String pseudoHeader = "";
    String tcpHeader = "";
    String frame = "";

    // Forty bytes is the size of the TCP header with pseudoheader.
    int tcpLen = 18 + (paddedMsg.length() >> 2);
    pseudoHeader = buildPseudoHeader(NetworkUtils.SOURCE_IP, NetworkUtils.DESTINATION_IP, NetworkUtils.PROTOCOL, tcpLen);
    
    int sqNo = ball.getColor() == Color.RED ? NetworkUtils.RED_SEQ_NO : NetworkUtils.BLUE_SEQ_NO;
    int ackNo = ball.getColor() == Color.RED ? NetworkUtils.RED_ACK_NO : NetworkUtils.BLUE_ACK_NO;
    
    // Build the first TCP Header without the correct checksum.
    tcpHeader = buildHeader(NetworkUtils.SOURCE_PORT, NetworkUtils.DESTINATION_PORT, 
            sqNo, ackNo, NetworkUtils.FLAGS, NetworkUtils.WIN_SIZE, -1, NetworkUtils.URG_PTR);
    frame = pseudoHeader + tcpHeader + paddedMsg;

    int checksum = NetworkUtils.checksumHexString(frame);

    // Now calculate the REAL header.
    tcpHeader = buildHeader(NetworkUtils.SOURCE_PORT, NetworkUtils.DESTINATION_PORT, 
            sqNo, ackNo, NetworkUtils.FLAGS, NetworkUtils.WIN_SIZE, checksum, NetworkUtils.URG_PTR);
    
    // Build the frame.
    frame = tcpHeader + paddedMsg;
        
    if (ball.getColor() == Color.RED) {
      NetworkUtils.RED_CHECKSUM = checksum;
    } else {
      NetworkUtils.BLUE_CHECKSUM = checksum;
    }
    
    ball.getNetworkData().frame = frame;
    ball.getNetworkData().tcpLength = tcpLen;
  }

  /**
   * 
   * @param srcIP
   * @param destIP
   * @param protocol
   * @param tcpLen
   * @return 
   */
  private static String buildPseudoHeader(int srcIP, int destIP, int protocol, int tcpLen) {
    return NetworkUtils.convertToBinaryStr(srcIP, 32)
            + NetworkUtils.convertToBinaryStr(destIP, 32)
            + "00000000"
            + NetworkUtils.convertToBinaryStr(protocol, 8)
            + NetworkUtils.convertToBinaryStr(tcpLen, 10);
  }

  /**
   * 
   * @param srcPort
   * @param destPort
   * @param seq
   * @param ack
   * @param flags
   * @param winSize
   * @param checksum
   * @param urgPtr
   * @return 
   */
  private static String buildHeader(int srcPort, int destPort, int seq, int ack, int flags, int winSize, int checksum, int urgPtr) {
    return NetworkUtils.convertToBinaryStr(srcPort, 16)
            + NetworkUtils.convertToBinaryStr(destPort, 16)
            + NetworkUtils.convertToBinaryStr(seq, 32)
            + NetworkUtils.convertToBinaryStr(ack, 32)
            + NetworkUtils.convertToBinaryStr(flags, 16)
            + NetworkUtils.convertToBinaryStr(winSize, 16) 
            + NetworkUtils.convertToBinaryStr(checksum == -1 ? 0 : checksum, 16)
            + NetworkUtils.convertToBinaryStr(urgPtr, 16);
  }
}
