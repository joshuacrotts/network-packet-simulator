//=============================================================================================//
// FILENAME :       NetworkBackground.java
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
// AUTHOR   :   Joshua Crotts        START DATE :    23 Aug. 2020
// CLASS    :   CSC - 677 
// SEMESTER :   FALL 2020
//
//=============================================================================================//
package com.joshuacrotts.uncg.view;

import com.joshuacrotts.uncg.Simulator;
import com.joshuacrotts.uncg.model.DestinationHost;
import com.joshuacrotts.uncg.model.SourceHost;
import java.awt.Graphics2D;

public class NetworkBackground {

  private final Simulator simulator;

  private final SourceHost source;
  private final DestinationHost dest;

  public NetworkBackground(Simulator simulator) {
    this.simulator = simulator;

    this.source = new SourceHost(simulator);
    this.dest = new DestinationHost(simulator);
  }

  /**
   *
   */
  public void updateBackground() {
    if (this.simulator.getRedBall().getX() < this.simulator.getWidth() / 2) {
      this.source.updateSource();
    }

    if (this.simulator.getRedBall().getX() >= this.simulator.getWidth() / 2) {
      this.dest.updateDestination();
    }
  }

  /**
   *
   * @param g2
   */
  public void drawBackground(Graphics2D g2) {
    this.source.drawSource(g2);
    this.dest.drawDestination(g2);
  }
}
