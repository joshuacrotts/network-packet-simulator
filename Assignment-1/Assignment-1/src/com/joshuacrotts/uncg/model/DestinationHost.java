//=============================================================================================//
// FILENAME :       DestinationHost.java
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
// AUTHOR   :   Joshua Crotts        START DATE :    08 Aug. 2020
// CLASS    :   CSC - 677 
// SEMESTER :   FALL 2020
//
//=============================================================================================//
package com.joshuacrotts.uncg.model;

import com.joshuacrotts.uncg.Simulator;
import com.joshuacrotts.view.ApplicationRectangle;
import com.joshuacrotts.view.DataLinkRectangle;
import com.joshuacrotts.view.NetworkRectangle;
import com.joshuacrotts.view.OSIRectangle;
import com.joshuacrotts.view.PhysicalRectangle;
import com.joshuacrotts.view.PresentationRectangle;
import com.joshuacrotts.view.SessionRectangle;
import com.joshuacrotts.view.TransportRectangle;
import java.awt.Graphics2D;

public class DestinationHost {

  private final int H_START_OFFSET = 300;
  private final int Y_START_OFFSET = 50;

  private final ApplicationRectangle app;
  private final DataLinkRectangle dl;
  private final NetworkRectangle ntwk;
  private final PhysicalRectangle phys;
  private final PresentationRectangle pres;
  private final SessionRectangle session;
  private final TransportRectangle trans;

  public DestinationHost(Simulator simulator) {

    int H_POS = simulator.getSimulatorFrameWidth() - H_START_OFFSET - OSIRectangle.RECT_WIDTH;
    System.out.println(H_POS);
    
    this.app = new ApplicationRectangle(simulator, H_POS, 50);
    this.dl = new DataLinkRectangle(simulator, H_POS, 150);
    this.ntwk = new NetworkRectangle(simulator, H_POS, 250);
    this.phys = new PhysicalRectangle(simulator, H_POS, 350);
    this.pres = new PresentationRectangle(simulator, H_POS, 450);
    this.session = new SessionRectangle(simulator, H_POS, 550);
    this.trans = new TransportRectangle(simulator, H_POS, 650);
  }

  /**
   *
   */
  public void updateDestination() {
    this.app.update();
    this.pres.update();
    this.session.update();
    this.trans.update();
    this.ntwk.update();
    this.dl.update();
    this.phys.update();
  }

  /**
   *
   * @param g2
   */
  public void drawDestination(Graphics2D g2) {
    this.app.drawRectangle(g2);
    this.pres.drawRectangle(g2);
    this.session.drawRectangle(g2);
    this.trans.drawRectangle(g2);
    this.ntwk.drawRectangle(g2);
    this.dl.drawRectangle(g2);
    this.phys.drawRectangle(g2);
  }
}
