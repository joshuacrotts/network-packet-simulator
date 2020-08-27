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
// AUTHOR   :   Joshua Crotts        START DATE :    23 Aug. 2020
// CLASS    :   CSC - 677 
// SEMESTER :   FALL 2020
//
//=============================================================================================//
package com.joshuacrotts.uncg;

import com.joshuacrotts.uncg.dijkstra.Dijkstra;
import com.joshuacrotts.uncg.dijkstra.Vertex;
import com.joshuacrotts.uncg.model.Ball;
import com.joshuacrotts.uncg.model.MouseModel;
import com.joshuacrotts.uncg.model.PauseButton;
import com.joshuacrotts.uncg.model.ResumeButton;
import com.joshuacrotts.uncg.model.StopButton;
import com.joshuacrotts.uncg.model.UIButton;
import com.joshuacrotts.uncg.view.NetworkBackground;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
  private TCPSteps tcpSteps;
  private Ball redBall;
  private Ball blueBall;

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

  private static List<Vertex> path;

  public Simulator() {

    /**
     * VERY PRELIMINARY TESTING
     */
    Dijkstra dijkstra = new Dijkstra();

    Vertex v1 = new Vertex("P1", 100, 100);
    Vertex v2 = new Vertex("P2", 100, 500);
    Vertex v3 = new Vertex("P3", 200, 500);
    Vertex v4 = new Vertex("P4", 400, 300);
    Vertex v5 = new Vertex("P5", 400, 700);
    Vertex v6 = new Vertex("P6", 700, 300);
    Vertex v7 = new Vertex("P7", 700, 700);
    Vertex v8 = new Vertex("P8", 800, 730);
    Vertex v9 = new Vertex("P9", 900, 730);
    Vertex v10 = new Vertex("P10", 900, 100);

    dijkstra.addVertex(v1);
    dijkstra.addVertex(v2);
    dijkstra.addVertex(v3);
    dijkstra.addVertex(v4);
    dijkstra.addVertex(v5);
    dijkstra.addVertex(v6);
    dijkstra.addVertex(v7);
    dijkstra.addVertex(v8);
    dijkstra.addVertex(v9);
    dijkstra.addVertex(v10);

    dijkstra.addEdge("P1", "P2");
    dijkstra.addEdge("P1", "P3");
    dijkstra.addEdge("P2", "P3");
    dijkstra.addEdge("P3", "P4");
    dijkstra.addEdge("P3", "P5");
    dijkstra.addEdge("P4", "P6");
    dijkstra.addEdge("P5", "P7");
    dijkstra.addEdge("P6", "P8");
    dijkstra.addEdge("P7", "P8");
    dijkstra.addEdge("P8", "P9");
    dijkstra.addEdge("P9", "P10");

    Simulator.path = dijkstra.getDijkstraPath("P1", "P10");
    /* ******** */
    this.pauseButton = new PauseButton(this);
    this.resumeButton = new ResumeButton(this);
    this.stopButton = new StopButton(this);

    super.add(this.pauseButton);
    super.add(this.resumeButton);
    super.add(this.stopButton);

    this.promptMessageInput();

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
    this.tcpSteps = new TCPSteps(this.osiModel);

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
//        Vertex headTo = Simulator.path.get(0);
//        if (this.redBall.getX() != headTo.getX() || this.redBall.getY() != headTo.getY() ) {
//          this.redBall.moveTo(headTo.getX(), headTo.getY());
//        } else {
//          Simulator.path.remove(0);
//        }
        this.blueBall.moveTo(this.mouse.getMouseX(), this.mouse.getMouseY());
        this.osiModel.updateBackground();
        this.tcpSteps.checkTCPSteps(redBall);
        this.tcpSteps.checkTCPSteps(blueBall);
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

  /**
   *
   * @return
   */
  private void promptMessageInput() {
    String redMsg = JOptionPane.showInputDialog(this.parentFrame, "", "Enter a message for red: ", JOptionPane.QUESTION_MESSAGE);
    String blueMsg = JOptionPane.showInputDialog(this.parentFrame, "", "Enter a message for blue: ", JOptionPane.QUESTION_MESSAGE);
    NetworkData redData = new NetworkData(redMsg);
    NetworkData blueData = new NetworkData(blueMsg);
    this.redBall = new Ball(20, 20, 2, 0, Color.RED, redData);
    this.blueBall = new Ball(60, 60, 2, 0, Color.BLUE, blueData);
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
