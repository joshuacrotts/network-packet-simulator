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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Ball {

  private final NetworkData networkData;

  private final Point2D.Double pos;
  private final Color color;
  private final Timer trailTimer;
  private final BallTrail ballTrail;

  private static final int DEFAULT_CHASE_VELOCITY = 1;
  private static final int BALL_WIDTH = 20;
  private static final int BALL_HEIGHT = 20;
  private static final int TRAIL_TIMER = 200; // 200 MS

  private double dx, dy;

  public Ball(int x, int y, int dx, int dy, Color color, NetworkData networkData) {
    this.color = color;
    this.pos = new Point2D.Double(x, y);
    this.dx = dx;
    this.dy = dy;
    this.networkData = networkData;
    this.trailTimer = new Timer();
    this.ballTrail = new BallTrail(this);
    this.trailTimer.schedule(this.ballTrail, 0, TRAIL_TIMER);
  }

  /**
   * Updates the ball position to reflect any changes with velocity.
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

    this.updateBall();
  }

  /**
   * Precisely moves a ball towards a point. Precisely means that the velocity
   * is calculated according to the hypotenuse formed by the current position
   * and the point that it's trying to head towards. One important note is that,
   * because the velocities and position vectors are doubles, you NEED to do the
   * comparison for position with floating-point difference using some epsilon.
   *
   * @param x
   * @param y
   */
  public void preciseMove(int x, int y) {
    double changeX = x - this.getX();
    double changeY = y - this.getY();
    double dir = Math.atan2(changeY, changeX);
    double speed = 2;

    this.setX(( this.getX() + ( speed * Math.cos(dir) ) ));
    this.setY(( this.getY() + ( speed * Math.sin(dir) ) ));
  }

  /**
   *
   * @param g2
   */
  public void drawBall(Graphics2D g2) {
    g2.setColor(this.color);
    g2.fillOval((int) this.getX(), (int) this.getY(),
            this.BALL_WIDTH, this.BALL_HEIGHT);

  }

  /**
   * Draws the trail associated with the ball that is populated overtime.
   *
   * @param g2
   */
  private void drawBallTrail(Graphics2D g2) {
    for (int i = 0; i < this.ballTrail.trail.size(); i ++) {
      g2.draw(this.ballTrail.trail.get(i));
    }
  }

  //====================== ACCESSORS/MUTATORS ============================//
  public NetworkData getNetworkData() {
    return this.networkData;
  }

  public Color getColor() {
    return this.color;
  }

  public double getX() {
    return this.pos.x;
  }

  public void setX(double x) {
    this.pos.x = x;
  }

  public double getY() {
    return this.pos.y;
  }

  public void setY(double y) {
    this.pos.y = y;
  }

  public double getVelX() {
    return dx;
  }

  public void setVelX(double dx) {
    this.dx = dx;
  }

  public double getVelY() {
    return dy;
  }

  public void setVelY(double dy) {
    this.dy = dy;
  }

  private class BallTrail extends TimerTask {

    private List<Ellipse2D.Double> trail;
    private Ball parentBall;

    protected BallTrail(Ball parent) {
      this.parentBall = parent;
      this.trail = new ArrayList<>();
    }

    @Override
    public void run() {
      this.trail.add(new Ellipse2D.Double(parentBall.getX() + parentBall.BALL_WIDTH / 2,
              parentBall.getY() + parentBall.BALL_HEIGHT / 2,
              3, 3));
    }

    public List<Ellipse2D.Double> getTrail() {
      return this.trail;
    }
  }
}
