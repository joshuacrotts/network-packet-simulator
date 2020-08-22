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
// AUTHOR   :   Joshua Crotts        START DATE :    08 Aug. 2020
// CLASS    :   CSC - 677 
// SEMESTER :   FALL 2020
//
//=============================================================================================//
package com.joshuacrotts.view;

import com.joshuacrotts.uncg.Simulator;
import java.awt.Color;
import java.awt.Graphics2D;

public class PhysicalRectangle extends OSIRectangle {

  private static final Color physicalActiveColor = new Color(224, 224, 224);

  public PhysicalRectangle(Simulator simulator, int x, int y) {
    super(simulator, "PHYSICAL");
    super.x = x;
    super.y = y;
    super.setActiveColor(physicalActiveColor);
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
}
