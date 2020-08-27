//=============================================================================================//
// FILENAME :       TCPSteps.java
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

import com.joshuacrotts.uncg.model.Ball;
import com.joshuacrotts.uncg.model.DestinationHost;
import com.joshuacrotts.uncg.model.SourceHost;
import com.joshuacrotts.uncg.model.TransportLayer;
import com.joshuacrotts.uncg.view.NetworkBackground;

public class TCPSteps {

  private final SourceHost source;
  private final DestinationHost destination;

  /* Variables for determining if we need to keep activating the TCP steps or not. */
  private boolean hasTransportRed = false;
  private boolean hasTransportBlue = false;

  public TCPSteps(NetworkBackground networkBackground) {
    this.source = networkBackground.getSource();
    this.destination = networkBackground.getDestination();
  }

  public void checkTCPSteps(Ball ball) {
    if (this.source.getTrans().isRedActive() && !this.hasTransportRed) {
      TransportLayer.transport(ball);
      this.hasTransportRed = true;
    }

    if (this.source.getTrans().isBlueActive() && !this.hasTransportBlue) {
      TransportLayer.transport(ball);
      this.hasTransportBlue = true;
    }
  }
}
