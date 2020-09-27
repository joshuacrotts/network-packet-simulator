//=============================================================================================//
// FILENAME :       OSIType.java
//
// DESCRIPTION :    Enum to keep track of the different OSI rectangle types
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
// AUTHOR   :   Joshua Crotts        START DATE :    25 Aug. 2020
// CLASS    :   CSC - 677 
// SEMESTER :   FALL 2020
//
//=============================================================================================//
package com.joshuacrotts.uncg.model;

public enum OSIType {
  TRANSPORT {
    @Override
    public String toString() {
      return "TRANSPORT";
    }
  },
  PHYSICAL {
    @Override
    public String toString() {
      return "PHYSICAL";
    }
  },
  DATALINK {
    @Override
    public String toString() {
      return "DATALINK";
    }
  },
  PRESENTATION {
    @Override
    public String toString() {
      return "PRESENTATION";
    }
  },
  SESSION {
    @Override
    public String toString() {
      return "SESSION";
    }
  },
  APPLICATION {
    @Override
    public String toString() {
      return "APPLICATION";
    }
  },
  NETWORK {
    @Override
    public String toString() {
      return "NETWORK";
    }
  }
}
