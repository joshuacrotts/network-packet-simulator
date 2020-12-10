//=============================================================================================//
// FILENAME :       DataLinkLayer.java
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
package com.joshuacrotts.uncg.model;

import com.joshuacrotts.uncg.NetworkData;
import com.joshuacrotts.uncg.NetworkUtils;

public class DataLinkLayer {

  /**
   * Each ball has its own respective network data.
   *
   * @param ball
   */
  public static void datalink(Ball ball) {
    NetworkData data = ball.getNetworkData();
    
    String paddedMsg = data.frame;
    while (paddedMsg.length() % 4 != 0) {
      paddedMsg += "0";
    }
    
    String frame = "";
    String macHeader = "";
    String payload = ball.getNetworkData().frame;
    
    macHeader = buildEthernetHeader(NetworkUtils.PREAMBLE, NetworkUtils.SFD, NetworkUtils.DESTINATION_MAC, NetworkUtils.SOURCE_MAC, NetworkUtils.ETHERNET_TYPE);
    
    frame = macHeader + payload;
    
    ball.getNetworkData().frame = frame;
    ball.getNetworkData().ipDatagram = payload;
  }
  
  /**
   * 
   * @param preamble
   * @param sfd
   * @param destMac
   * @param srcMac
   * @param type
   * @param payload
   * @return
   */
  private static String buildEthernetHeader(long preamble, long sfd, long destMac, long srcMac, long type) {
    return NetworkUtils.convertToHexStr(preamble, 14) 
        + NetworkUtils.convertToHexStr(sfd, 2)
        + NetworkUtils.convertToHexStr(destMac, 12) 
        + NetworkUtils.convertToHexStr(srcMac, 12)
        + NetworkUtils.convertToHexStr(type, 4);
  }
}
