//=============================================================================================//
// FILENAME :       OSIRectangle.java
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
package com.joshuacrotts.uncg.view;

import com.joshuacrotts.uncg.Simulator;
import com.joshuacrotts.uncg.model.Ball;
import com.joshuacrotts.uncg.model.HostType;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class OSIRectangle extends Rectangle {

  public static final int RECT_WIDTH = 120;
  public static final int RECT_HEIGHT = 50;

  private final Simulator simulator;

  // This will need to be either isRedActive or isBlueActive or something to 
  // that effect later on down the road. Perhaps an array?
  private boolean isActive = false;

  private Color activeColor;
  private Color inactiveColor;
  private final String osiType;
  private final HostType hostType;

  public OSIRectangle(Simulator simulator, HostType hostType, String osiType) {
    this.osiType = osiType;
    this.hostType = hostType;
    this.simulator = simulator;
    this.width = RECT_WIDTH;
    this.height = RECT_HEIGHT;
    this.inactiveColor = Color.WHITE;
  }

  /**
   * Right now, this just sets the osi rectangle to be active if the y
   * coordinate is lower than the y coordinate of the rectangle.
   *
   * @param ball
   */
  public void updateOSIRectangle(Ball ball) {
    if (ball.getY() >= this.y) {
      this.setActive(true);
    }
  }

  /**
   *
   * @param g2
   */
  public void drawOSIRectangle(Graphics2D g2) {
    /* First, fill with the color that it has when either active or inactive. */
    g2.setColor(this.isActive() ? this.getActiveColor() : this.getInactiveColor());
    g2.fill(this);

    /* Now draw the black outline. */
    g2.setColor(Color.BLACK);
    g2.draw(this);

    /* Now draw the string with the type of OSI rectangle. */
    FontMetrics fm = g2.getFontMetrics();
    g2.setColor(Color.BLACK);

    /* Position the text in the center of our rectangle. */
    int textX = this.x + (this.width - fm.stringWidth(osiType)) / 2;
    int textY = this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent();

    g2.drawString(this.osiType, textX, textY);
  }

  /**
   *
   */
  public abstract void update();

  /**
   *
   * @param g2
   */
  public abstract void drawRectangle(Graphics2D g2);

  public void setActiveColor(Color c) {
    this.activeColor = c;
  }

  public void setInactiveColor(Color c) {
    this.inactiveColor = c;
  }

  public Color getActiveColor() {
    return activeColor;
  }

  public Color getInactiveColor() {
    return inactiveColor;
  }

  public boolean isActive() {
    return this.isActive;
  }

  public void setActive(boolean b) {
    this.isActive = b;
  }

  public Simulator getSimulator() {
    return this.simulator;
  }

  public HostType getHostType() {
    return this.hostType;
  }
}
