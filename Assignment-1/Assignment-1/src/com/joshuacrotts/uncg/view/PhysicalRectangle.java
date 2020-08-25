//=============================================================================================//
// FILENAME :       PhysicalRectangle.java
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
import com.joshuacrotts.uncg.StdOps;
import com.joshuacrotts.uncg.model.HostType;
import com.joshuacrotts.uncg.model.OSIType;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;

public class PhysicalRectangle extends OSIRectangle implements MouseListener, MouseMotionListener {

  private static final Color PHYSICAL_ACTIVE_COLOR = new Color(224, 224, 224);

  public PhysicalRectangle(Simulator simulator, HostType hostType, int x, int y) {
    super(simulator, hostType, OSIType.PHYSICAL);
    super.x = x;
    super.y = y;
    super.setActiveColor(PHYSICAL_ACTIVE_COLOR);
    super.setMouseOverColor(StdOps.changeColorBrightness(PHYSICAL_ACTIVE_COLOR, COLOR_BRIGHTNESS_OFFSET));
  }

  @Override
  public void update() {
    super.updateOSIRectangle(super.getSimulator().getRedBall());
    super.updateOSIRectangle(super.getSimulator().getBlueBall());
  }

  @Override
  public void drawRectangle(Graphics2D g2) {
    super.drawOSIRectangle(g2);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON3
            && StdOps.isMouseOver(e.getX(), e.getY(), this.x, this.y, this.width, this.height)) {

      super.getSimulator().setPaused(true);

      String host = super.getHostType().toString();

      String redMsg = super.isActive() ? super.getSimulator().getRedBall().getNetworkData().getMessage() : "Red Ball has not reached " + this.getOSIType().toString() + " Layer yet for " + host + ".";
      JOptionPane.showMessageDialog(super.getSimulator(), redMsg, "Red Data at " + this.getOSIType().toString() + " Layer for " + host, JOptionPane.INFORMATION_MESSAGE);
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (StdOps.isMouseOver(e.getX(), e.getY(), this.x, this.y, this.width, this.height)) {
      this.setActiveColor(this.getMouseOverColor());
    } else {
      this.setActiveColor(PHYSICAL_ACTIVE_COLOR);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
  }
}
