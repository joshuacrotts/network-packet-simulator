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
import com.joshuacrotts.uncg.dijkstra.Edge;
import com.joshuacrotts.uncg.dijkstra.Vertex;
import com.joshuacrotts.uncg.model.Ball;
import com.joshuacrotts.uncg.model.MouseModel;
import com.joshuacrotts.uncg.model.PauseButton;
import com.joshuacrotts.uncg.model.ResumeButton;
import com.joshuacrotts.uncg.model.StopButton;
import com.joshuacrotts.uncg.model.UIButton;
import com.joshuacrotts.uncg.view.NetworkBackground;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.Iterator;
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

  /* Dijkstra testing. */
  private static List<Vertex> redPath;
  private static List<Vertex> bluePath;

  public Simulator() {
    this.initDijkstraPaths();

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
    this.osiModel.drawBackground(g2);
    this.drawDijkstra(g2);
    this.drawBalls(g2);
  }

  /**
   *
   */
  private void updateLoop() {
    this.timer = new Timer(FRAME_DELAY, (ActionEvent e) -> {
      if (!this.isPaused) {

        //       ALL OF THIS STUFF IS DIJKSTRA TEST STUFF!!!
        //       BE AWARE THAT IT CURRENTLY CRASHES BECAUSE THE ARRAYLIST
        //       GOES OUT OF BOUNDS                                        
        /* Red path dijkstra. */
        Vertex headTo = Simulator.redPath.get(0);
        if (this.redBall.getX() != headTo.getX() || this.redBall.getY() != headTo.getY()) {
          this.redBall.moveTo(headTo.getX(), headTo.getY());
        } else {
          Simulator.redPath.remove(0);
        }

        /* Blue path dijkstra. */
        headTo = Simulator.bluePath.get(0);
        if (this.blueBall.getX() != headTo.getX() || this.blueBall.getY() != headTo.getY()) {
          this.blueBall.moveTo(headTo.getX(), headTo.getY());
        } else {
          Simulator.bluePath.remove(0);
        }
        // ===== END DIJKSTRA TEST STUFF!!! ====== /

//        this.redBall.moveTo(this.mouse.getMouseX(), this.mouse.getMouseY());
//        this.blueBall.moveTo(this.mouse.getMouseX(), this.mouse.getMouseY());
        this.osiModel.updateBackground();

        /* Checks the progress of the TCP algorithm. */
        this.tcpSteps.checkTCPSteps(redBall);
        this.tcpSteps.checkTCPSteps(blueBall);

        /* Redraws the JPanel. */
        repaint();
      }
      this.updateUIComponents();
    }
    );

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
   * @param g2
   */
  private void drawDijkstra(Graphics2D g2) {
    Iterator<Vertex> it = Dijkstra.vertices.iterator();

    while (it.hasNext()) {
      Vertex v = it.next();
      for (Edge e : v.adjacencyList) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(10));
        g2.drawLine(e.source.getX() + 10, e.source.getY() + 10, e.destination.getX() + 10, e.destination.getY() + 10);
        g2.setStroke(new BasicStroke(1));
      }
    }
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

  /**
   *
   */
  private void initDijkstraPaths() {
    /* Instantiates the vertices with a string ID (just for naming), and position. */
    Vertex A = new Vertex("A", 100, 100);//1
    Vertex B = new Vertex("B", 100, 600);//2
    Vertex C = new Vertex("C", 500, 600);//3
    Vertex D = new Vertex("D", 600, 500);//4
    Vertex E = new Vertex("E", 600, 700);//5
    Vertex F = new Vertex("F", 850, 500);//6
    Vertex G = new Vertex("G", 850, 700);//7
    Vertex H = new Vertex("H", 1000, 600);//8
    Vertex I = new Vertex("I", 1300, 600);//19
    Vertex J = new Vertex("J", 1300, 450);//10
    Vertex K = new Vertex("K", 1300, 300);//11
    Vertex L = new Vertex("L", 1300, 100);//12

    /* Adds the edges between the vertices. All this does is assign the adjacency
       list values. */
    Dijkstra.addEdge(C, E);
    Dijkstra.addEdge(A, B);
    Dijkstra.addEdge(H, L);
    Dijkstra.addEdge(G, J);

    Dijkstra.addEdge(B, C);
    Dijkstra.addEdge(C, D);
    Dijkstra.addEdge(C, E);
    Dijkstra.addEdge(D, E);
    Dijkstra.addEdge(D, F);
    Dijkstra.addEdge(F, E);
    Dijkstra.addEdge(F, G);
    Dijkstra.addEdge(E, G);
    Dijkstra.addEdge(F, H);
    Dijkstra.addEdge(G, H);
    Dijkstra.addEdge(H, I);
    Dijkstra.addEdge(I, J);
    Dijkstra.addEdge(J, K);
    Dijkstra.addEdge(K, L);

    Dijkstra d = new Dijkstra();

    d.dijkstra(A);

    /* Prints the adjacency lists of each node. */
//    System.out.println(A.adjacencyList);
//    System.out.println(B.adjacencyList);
//    System.out.println(C.adjacencyList);
//    System.out.println(D.adjacencyList);
//    System.out.println(E.adjacencyList);
//    System.out.println(F.adjacencyList);
//    System.out.println(G.adjacencyList);
//    System.out.println(H.adjacencyList);
//    System.out.println(I.adjacencyList);
//    System.out.println(J.adjacencyList);
//    System.out.println(K.adjacencyList);
//    System.out.println(L.adjacencyList);
    Simulator.redPath = d.getDijkstraPath(L);
    Simulator.bluePath = d.getDijkstraPath(L);
  }

  //====================== ACCESSORS/MUTATORS ============================//
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
