//=============================================================================================//
// FILENAME :       Simulator.java
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
package com.joshuacrotts.uncg;

import com.joshuacrotts.uncg.model.Ball;
import com.joshuacrotts.uncg.model.MouseModel;
import com.joshuacrotts.uncg.model.PauseButton;
import com.joshuacrotts.uncg.model.ResumeButton;
import com.joshuacrotts.uncg.model.StopButton;
import com.joshuacrotts.uncg.model.UIButton;
import com.joshuacrotts.view.NetworkBackground;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Simulator extends JPanel {

  /**
   * Parent frame and timer/thread variables.
   */
  private final JFrame parentFrame;
  private final MouseModel mouse;
  private Timer timer;

  /**
   * JPanel-related buttons.
   */
  private final UIButton pauseButton;
  private final UIButton resumeButton;
  private final UIButton stopButton;
  private final NetworkBackground osiModel;

  /**
   * Other simulator-related variables and objects.
   */
  private final Ball redBall;
  private final Ball blueBall;

  /**
   * Status variables.
   */
  private boolean isRunning = false;
  private boolean isPaused = false;

  /**
   * Miscellaneous sizing and positioning variables.
   */
  private static final int FRAME_WIDTH = 1366;
  private static final int FRAME_HEIGHT = 768;
  private static final int FRAME_DELAY = 17; // 17 ms is approximately 60 fps.

  private static final String TITLE = "CSC-677 - Assignment #1";

  public Simulator() {
    this.pauseButton = new PauseButton(this);
    this.resumeButton = new ResumeButton(this);
    this.stopButton = new StopButton(this);

    super.add(this.pauseButton);
    super.add(this.resumeButton);
    super.add(this.stopButton);

    this.parentFrame = new JFrame(TITLE);
    this.parentFrame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    this.parentFrame.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    this.parentFrame.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    this.parentFrame.setResizable(false);
    this.parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.parentFrame.getContentPane().add(this);
    this.parentFrame.pack();
    this.parentFrame.setLocationRelativeTo(null);
    this.parentFrame.setVisible(true);
    this.mouse = new MouseModel();
    super.addMouseListener(mouse);
    super.addMouseMotionListener(mouse);

    this.osiModel = new NetworkBackground(this);
    this.redBall = new Ball(20, 20, 2, 0, Color.RED);
    this.blueBall = new Ball(60, 60, 2, 0, Color.BLUE);

  }

  /**
   * Starts the simulation.
   */
  public void startSimulation() {
    if (this.isRunning) {
      return;
    }

    this.isRunning = true;
    this.updateLoop();
  }

  /**
   *
   */
  public void stopSimulation() {
    if (!this.isRunning) {
      return;
    }

    this.isRunning = false;
    System.exit(0);
  }

  /**
   *
   * @param g
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    this.drawBackground(g2);
    this.drawBalls(g2);
    this.osiModel.drawBackground(g2);
  }

  /**
   *
   */
  private void updateLoop() {
    this.timer = new Timer(FRAME_DELAY, (ActionEvent e) -> {
      if (!this.isPaused) {
        this.redBall.moveTo(this.mouse.getMouseX(), this.mouse.getMouseY());
        this.blueBall.moveTo(this.mouse.getMouseX(), this.mouse.getMouseY());
        this.osiModel.updateBackground();
        repaint();
      }

      this.updateUIComponents();
    });
    this.timer.start();
  }

  /**
   * Updates the buttons to disable/enable pause and resume buttons.
   */
  private void updateUIComponents() {
    this.pauseButton.setEnabled(!this.isPaused);
    this.resumeButton.setEnabled(this.isPaused);
  }

  /**
   *
   * @param g2
   */
  private void drawBackground(Graphics2D g2) {
    g2.setColor(Color.WHITE);
    g2.fillRect(0, 0, Simulator.FRAME_WIDTH, Simulator.FRAME_HEIGHT);
  }

  /**
   *
   * @param g2
   */
  private void drawBalls(Graphics2D g2) {
    this.redBall.drawBall(g2);
    this.blueBall.drawBall(g2);
  }

  public int getSimulatorFrameWidth() {
    return this.parentFrame.getWidth();
  }

  public int getSimulatorFrameHeight() {
    return this.parentFrame.getHeight();
  }

  public boolean isRunning() {
    return this.isRunning;
  }

  public void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }

  public boolean isPaused() {
    return this.isPaused;
  }

  public void setPaused(boolean isPaused) {
    this.isPaused = isPaused;
  }

  public Ball getRedBall() {
    return redBall;
  }

  public Ball getBlueBall() {
    return blueBall;
  }
}
