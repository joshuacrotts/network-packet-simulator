//=============================================================================================//
// FILENAME :       NetworkUtils.java
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
package com.joshuacrotts.uncg;

public class NetworkUtils {

//  public static final int SOURCE_PORT = 0x01bb;
//  public static final int DESTINATION_PORT = 0xd49d;
//  public static final int RED_SEQ_NO = 0x563959de;
//  public static final int BLUE_SEQ_NO = 0xab2cc4bd;
//  public static final int RED_ACK_NO = 0xd7532d3e; // Should this be related to SEQ?
//  public static final int BLUE_ACK_NO = 0x7e935338;
//  public static int RED_CHECKSUM = 0;
//  public static int BLUE_CHECKSUM = 0;

  /* Flags set for reserved section of TCP header (2 bytes). */
//  private static final int HLEN = 0b000001100 << 12;
//  private static final int RESERVED = 0 << 6;
//  private static final int URG_BIT = 0 << 5;
//  private static final int ACK_BIT = 0 << 4;
//  private static final int PSH_BIT = 0 << 3;
//  private static final int PST_BIT = 0 << 2;
//  private static final int RST_BIT = 0 << 1;
//  private static final int FIN_BIT = 0;
//
//  public static final int FLAGS = 0x5010;
//
//  public static final short WIN_SIZE = 0x0160;
//  public static final short URG_PTR = 0;
//
//  /**
//   * * PSEUDO TCP Header Data. **
//   */
//  /* Fix these as they do not follow RTC protocols (invalid address possibility. */
//  public static final int SOURCE_IP = 0xc0a8016a; // 1 dword.
//  public static final int DESTINATION_IP = 0x980D1117; // 1 dword.
//  public static final short PROTOCOL = 0x0006; // 1 unsigned byte.

  /**
   * * TCP Header data. **
   */
  public static final short SOURCE_PORT = getRandomHexShort();
  public static final short DESTINATION_PORT = getRandomHexShort();
  public static final int RED_SEQ_NO = getRandomHexInt();
  public static final int BLUE_SEQ_NO = getRandomHexInt();
  public static final int RED_ACK_NO = getRandomHexInt(); // Should this be related to SEQ?
  public static final int BLUE_ACK_NO = getRandomHexInt();
  public static int RED_CHECKSUM = 0;
  public static int BLUE_CHECKSUM = 0;

  /* Flags set for reserved section of TCP header (2 bytes). */
//  private static final int HLEN = 0b000001100 << 12;
//  private static final int RESERVED = 0 << 6;
//  private static final int URG_BIT = 0 << 5;
//  private static final int ACK_BIT = 0 << 4;
//  private static final int PSH_BIT = 0 << 3;
//  private static final int PST_BIT = 0 << 2;
//  private static final int RST_BIT = 0 << 1;
//  private static final int FIN_BIT = 0;

  public static final int FLAGS = 0x5010;

  public static final short WIN_SIZE = 0x0160;
  public static final short URG_PTR = 0;

  /**
   * * PSEUDO TCP Header Data. **
   */
  /* Fix these as they do not follow RTC protocols (invalid address possibility. */
  public static final int SOURCE_IP = getRandomHexInt(); // 1 dword.
  public static final int DESTINATION_IP = getRandomHexInt(); // 1 dword.
  public static final short PROTOCOL = 0x0006; // 1 unsigned byte.
  /**
   * IP Datagram data.
   */
  public static final byte VERSION = 0x04; // 1 nibble.
  public static final byte IHL = 0x05; // Min is 20 bytes, 5 DWORDS, 1 nibble.
  public static final byte SERVICE_TYPE = 0; // 1 byte.
  public static final short IP_IDENTIFICATION = getRandomHexShort(); // 1 word.
  public static final short TTL = 255; // 1 unsigned byte.

  /**
   *
   * @return
   */
  public static short getRandomHexShort() {
    return (short) StdOps.randomInt(0, Short.MAX_VALUE - 1);
  }

  /**
   *
   * @return
   */
  public static int getRandomHexInt() {
    return StdOps.randomInt(0, Integer.MAX_VALUE - 1);
  }

  /**
   *
   * @param hexOne
   * @param hexTwo
   * @return
   */
  public static int xorHexString(String hexOne, String hexTwo) {
    if (hexOne.length() != hexTwo.length()) {
      return -1;
    }

    int checksum = 0;

    for (int i = 0; i < hexOne.length(); i++) {
      int n1 = Integer.parseInt("" + hexOne.charAt(i), 16);
      int n2 = Integer.parseInt("" + hexTwo.charAt(i), 16);

      checksum += n1 ^ n2;
    }

    return checksum;
  }

  /**
   * Computes the checksum of a HEX string. The resulting checksum is returned
   * as an integer truncated to 16 bits.
   *
   * @param hexString
   * @return
   */
  public static int checksumHexString(String hexString) {
    if (hexString.length() % 4 != 0) {
      throw new IllegalArgumentException("Your hex string should be a multiple of four in length!");
    }

    int checksum = 0;

    for (int i = 0; i < hexString.length(); i += 4) {
      // Look two bytes (hex!) ahead at a time.
      int nextTwoBytes = Integer.parseInt("" + hexString.substring(i, i + 4), 16);
      checksum += nextTwoBytes;
    }

    int remainder = checksum >> 16; // Compute anything to the left of the first four bytes.
    checksum &= 0xffff; // Strip all data to the left of the first four bytes.
    checksum += remainder; // Now, add what was to the left.
    checksum = 0xffff - checksum; // Compute the difference.
    return checksum;
  }

  /**
   * Converts an ASCII string into its corresponding hex value.
   *
   * @param ascii String of ascii characters.
   * @return new String with hex codes of chars.
   */
  public static String convertASCIIToHex(String ascii) {
    StringBuilder hex = new StringBuilder();

    for (int i = 0; i < ascii.length(); i++) {
      int code = (int) ascii.charAt(i);
      String hexValue = Integer.toHexString(code);
      hex.append(hexValue);
    }

    return hex.toString();
  }

  /**
   * Converts an ASCII string into its corresponding binary value.
   *
   * @param ascii String of ascii characters.
   * @return new String with binary codes of chars.
   */
  public static String convertASCIIToBinary(String ascii) {
    StringBuilder hex = new StringBuilder();

    for (int i = 0; i < ascii.length(); i++) {
      int code = (int) ascii.charAt(i);
      String hexValue = Integer.toBinaryString(code);
      hex.append(hexValue);
    }

    return hex.toString();
  }

  /**
   * Converts a binary String into a byte array representation.
   *
   * @param binary
   * @return
   */
  public static byte[] convertBinaryStringToByteArray(String binary) {
    byte[] b = new byte[binary.length()];

    for (int i = 0; i < binary.length(); i++) {
      b[i] = Byte.parseByte("" + binary.charAt(i));
    }

    return b;
  }

  /**
   *
   * @param n
   * @param pad
   * @return
   */
  public static String convertToBinaryStr(int n, int pad) {
    return String.format("%" + pad + "s", Integer.toBinaryString(n)).replaceAll(" ", "0");
  }

  /**
   *
   * @param n
   * @param pad
   * @return
   */
  public static String convertToHexStr(int n, int pad) {
    return String.format("%" + pad + "s", Integer.toHexString(n)).replaceAll(" ", "0").toUpperCase();
  }

  /**
   *
   * @param x
   * @param paddingCount
   * @return
   */
  public static String toHexStringPadded(int x, int paddingCount) {
    String s = Integer.toHexString(x);
    StringBuilder padding = new StringBuilder();
    for (int i = s.length(); i != paddingCount; i++) {
      padding.append("0");
    }

    return padding.append(s).toString().toUpperCase();
  }
}
