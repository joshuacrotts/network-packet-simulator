//=============================================================================================//
// FILENAME :       Ball.java
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
package com.joshuacrotts.uncg.model;

import com.joshuacrotts.uncg.NetworkData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Ball {

  private final NetworkData networkData;

  private final Point pos;
  private final Color color;

  private static final int DEFAULT_CHASE_VELOCITY = 1;
  private int dx, dy;
  private final int BALL_WIDTH = 20;
  private final int BALL_HEIGHT = 20;

  public Ball(int x, int y, int dx, int dy, Color color, NetworkData networkData) {
    this.color = color;
    this.pos = new Point(x, y);
    this.dx = dx;
    this.dy = dy;
    this.networkData = networkData;
  }

  /**
   * @TODO
   */
  public void updateBall() {
    this.setX(this.getX() + this.dx);
    this.setY(this.getY() + this.dy);
  }

  /**
   *
   * @param x
   * @param y
   */
  public void moveTo(int x, int y) {
    if (this.getX() < x) {
      this.setX(this.getX() + DEFAULT_CHASE_VELOCITY);
    } else if (this.getX() > x) {
      this.setX(this.getX() - DEFAULT_CHASE_VELOCITY);
    }

    if (this.getY() < y) {
      this.setY(this.getY() + DEFAULT_CHASE_VELOCITY);
    } else if (this.getY() > y) {
      this.setY(this.getY() - DEFAULT_CHASE_VELOCITY);
    }
  }

  /**
   *
   * @param g2
   */
  public void drawBall(Graphics2D g2) {
    g2.setColor(this.color);
    g2.fillOval(this.getX(), this.getY(), this.BALL_WIDTH, this.BALL_HEIGHT);
  }

  public NetworkData getNetworkData() {
    return this.networkData;
  }
  
  public Color getColor() {
    return this.color;
  }

  public int getX() {
    return this.pos.x;
  }

  public void setX(int x) {
    this.pos.x = x;
  }

  public int getY() {
    return this.pos.y;
  }

  public void setY(int y) {
    this.pos.y = y;
  }

  public int getVelX() {
    return dx;
  }

  public void setVelX(int dx) {
    this.dx = dx;
  }

  public int getVelY() {
    return dy;
  }

  public void setVelY(int dy) {
    this.dy = dy;
  }
}
