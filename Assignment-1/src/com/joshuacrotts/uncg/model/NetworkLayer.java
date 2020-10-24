//=============================================================================================//
// FILENAME :       NetworkLayer.java
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
package com.joshuacrotts.uncg.model;

import com.joshuacrotts.uncg.NetworkData;
import com.joshuacrotts.uncg.NetworkUtils;
import java.awt.Color;

public class NetworkLayer {

  /**
   * Each ball has its own respective network data.
   *
   * @param ball
   */
  public static void network(Ball ball) {
    NetworkData data = ball.getNetworkData();
    
    String paddedMsg = data.frame;
    while (paddedMsg.length() % 4 != 0) {
      paddedMsg += "0";
    }
    
    String ipHeader = "";
    String frame = "";
        
    int networkLength = 20 + (paddedMsg.length() >> 1);
    // Since the destination is different we have to get the correct one.
    long destIP = ball.getColor() == Color.RED ? NetworkUtils.MIDDLE_DESTINATION_IP : NetworkUtils.DESTINATION_IP;
    
    // Calculate the ip header without the checksum.
    ipHeader = buildHeader(NetworkUtils.VERSION, NetworkUtils.IHL, 0, networkLength, 
                           NetworkUtils.IP_IDENTIFICATION, NetworkUtils.FRAGMENT, 
                           NetworkUtils.TTL, NetworkUtils.PROTOCOL, 0, 
                           NetworkUtils.SOURCE_IP, destIP);
    
    int checksum = NetworkUtils.checksumHexString(ipHeader);
    
    // Now compute the REAL ip header.
    ipHeader = buildHeader(NetworkUtils.VERSION, NetworkUtils.IHL, 0, networkLength, 
        NetworkUtils.IP_IDENTIFICATION, NetworkUtils.FRAGMENT, 
        NetworkUtils.TTL, NetworkUtils.PROTOCOL, checksum, 
        NetworkUtils.SOURCE_IP, destIP);
    
    if (ball.getColor() == Color.RED) {
      NetworkUtils.RED_IP_CHECKSUM = checksum;
      NetworkUtils.RED_IP_LENGTH = networkLength;
    } else {
      NetworkUtils.BLUE_IP_CHECKSUM = checksum;
      NetworkUtils.BLUE_IP_LENGTH = networkLength;
    }
    
    frame = ipHeader + paddedMsg;
    
    ball.getNetworkData().frame = frame;
  }
  
  /**
   * 
   * @param version
   * @param ihl
   * @param tos
   * @param networkLength
   * @param ipIdentification
   * @param fragment
   * @param ttl
   * @param protocol
   * @param checksum
   * @param srcIP
   * @param destIP
   * @return
   */
  private static String buildHeader (int version, int ihl, int tos, int networkLength, 
                                     int ipIdentification, int fragment, int ttl, int protocol,
                                     int checksum, long srcIP, long destIP) {
    
    return NetworkUtils.convertToHexStr(version, 1) 
        + NetworkUtils.convertToHexStr(ihl, 1)
        + NetworkUtils.convertToHexStr(tos, 2) 
        + NetworkUtils.convertToHexStr(ipIdentification, 4)
        + NetworkUtils.convertToHexStr(fragment, 4)
        + NetworkUtils.convertToHexStr(ttl, 2)
        + NetworkUtils.convertToHexStr(protocol, 2)
        + NetworkUtils.convertToHexStr(checksum, 4)
        + NetworkUtils.convertToHexStrLong(srcIP, 8)
        + NetworkUtils.convertToHexStrLong(destIP, 8);
    
  }
}
