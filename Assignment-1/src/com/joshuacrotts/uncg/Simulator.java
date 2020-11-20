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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.joshuacrotts.uncg.dijkstra.Dijkstra;
import com.joshuacrotts.uncg.dijkstra.Edge;
import com.joshuacrotts.uncg.dijkstra.Vertex;
import com.joshuacrotts.uncg.model.Ball;
import com.joshuacrotts.uncg.model.DrawTrailButton;
import com.joshuacrotts.uncg.model.HelpButton;
import com.joshuacrotts.uncg.model.MouseModel;
import com.joshuacrotts.uncg.model.PauseButton;
import com.joshuacrotts.uncg.model.ResumeButton;
import com.joshuacrotts.uncg.model.Router;
import com.joshuacrotts.uncg.model.StopButton;
import com.joshuacrotts.uncg.model.UIButton;
import com.joshuacrotts.uncg.view.NetworkBackground;

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
  private final UIButton drawTrailsButton;
  private final UIButton stopButton;
  private final UIButton helpButton;
  private final NetworkBackground osiModel;

  /**
   * Other simulator-related variables and objects.
   */
  private Dijkstra redPathDijkstra;
  private Dijkstra bluePathDijkstra;
  private TCPSteps tcpSteps;
  private Ball redBall;
  private Ball blueBall;

  /**
   * Status variables.
   */
  private boolean isRunning = false;
  private boolean isPaused = false;
  private boolean isDrawingTrails = false;

  /**
   * Miscellaneous sizing and positioning variables.
   */
  private static final int FRAME_WIDTH = 1366;
  private static final int FRAME_HEIGHT = 768;
  private static final int FRAME_DELAY = 17; // 17 ms is approximately 60 fps.
  private static final String TITLE = "CSC-677 - Assignment #1";

  /*
   * Dijkstra testing.
   */
  private static Stack<Vertex> redPath;
  private static Stack<Vertex> bluePath;
  private final List<Router> routers;

  public Simulator() {
    // Creates the routers and initializes Dijkstra's path.
    this.routers = new LinkedList<>();
   

    // Creates the three status buttons.
    this.pauseButton = new PauseButton(this);
    this.resumeButton = new ResumeButton(this);
    this.drawTrailsButton = new DrawTrailButton(this);
    this.stopButton = new StopButton(this);
    this.helpButton = new HelpButton(this);

    // Adds the three buttons to the parent panel.
    super.add(this.pauseButton);
    super.add(this.resumeButton);
    super.add(this.drawTrailsButton);
    super.add(this.helpButton);
    super.add(this.stopButton);

    // Opens the dialog box.
    this.promptMessageInput();

    // Constructs the JFrame with the associated properties.
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

    // Adds the Mouse to the panel.
    this.mouse = new MouseModel();
    super.addMouseListener(this.mouse);
    super.addMouseMotionListener(this.mouse);
    
    // Initialize the dijkstra objects.
    this.redPathDijkstra = new Dijkstra();
    this.bluePathDijkstra = new Dijkstra();
    this.initDijkstraPaths();

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
    this.drawRouters(g2);
    this.drawBalls(g2);
  }

  /**
   *
   */
  private void updateLoop() {
    this.timer = new Timer(FRAME_DELAY, (ActionEvent e) -> {
      if (!this.isPaused) {
        pathFindRedBall();
        pathFindBlueBall();

        /*
         * Update the stack of OSI rectangles.
         */
        this.osiModel.updateBackground();

        /*
         * Updates the routers, if they exist.
         */
        this.updateRouters();

        /*
         * Checks the progress of the TCP algorithm.
         */
        this.tcpSteps.checkTCPSteps(this.redBall);
        this.tcpSteps.checkTCPSteps(this.blueBall);

        /*
         * Redraws the JPanel.
         */
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
   */
  private void pathFindRedBall() {
    /*
     * Red path dijkstra.
     */
    if (Simulator.redPath.isEmpty()) {
      return;
    }
    
    Vertex headTo = Simulator.redPath.peek();
    if (!StdOps.isDoubleEqual(headTo.getX(), this.redBall.getX(), 2)
            || !StdOps.isDoubleEqual(headTo.getY(), this.redBall.getY(), 2)) {
      this.redBall.preciseMove(headTo.getX(), headTo.getY());
    } else {
      Simulator.redPath.pop();
    }
  }

  /**
   *
   */
  private void pathFindBlueBall() {
    /*
     * Blue path dijkstra.
     */
    if (Simulator.bluePath.isEmpty()) {
      return;
    }
    
    Vertex headTo = Simulator.bluePath.peek();
    if (!StdOps.isDoubleEqual(headTo.getX(), this.blueBall.getX(), 2)
            || !StdOps.isDoubleEqual(headTo.getY(), this.blueBall.getY(), 2)) {
      this.blueBall.preciseMove(headTo.getX(), headTo.getY());
    } else {
      Simulator.bluePath.pop();
    }
  }

  /**
   * 
   */
  private void updateRouters() {
    Iterator<Router> it = this.routers.iterator();

    while (it.hasNext()) {
      Router r = it.next();
      r.updateRouter();
    }
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
    drawDijkstraEdges(g2);
    drawDijkstraVertices(g2);
  }

  /**
   *
   */
  private void drawDijkstraEdges(Graphics2D g2) {
    int EDGE_OFFSET = 10;
    Iterator<Vertex> it = this.redPathDijkstra.getVertices().iterator();

    /*
     * Iterate through the vertices and draw the associated edges.
     */
    while (it.hasNext()) {
      Vertex v = it.next();
      for (Edge e : v.adjacencyList) {

        /*
         * Draws an edge itself.
         */
        g2.setColor(Color.BLACK);
        g2.drawLine(e.source.getX() + EDGE_OFFSET,
                e.source.getY() + EDGE_OFFSET,
                e.destination.getX() + EDGE_OFFSET,
                e.destination.getY() + EDGE_OFFSET);

        /*
         * Draws the edge weight.
         */
        g2.setColor(Color.MAGENTA);
        g2.drawString(String.format("%.1f", e.distance),
                (int) ((e.source.getX() + e.destination.getX()) / 2),
                (int) ((e.source.getY() + e.destination.getY()) / 2));
      }
    }
  }

  /**
   *
   */
  private void drawDijkstraVertices(Graphics2D g2) {
    this.drawDijkstraPath(g2, this.redPathDijkstra, Color.RED);
    this.drawDijkstraPath(g2, this.bluePathDijkstra, Color.BLUE);
  }

  /**
   * @param g2
   */
  private void drawRouters(Graphics2D g2) {
    this.routers.forEach((r) -> {
      r.drawRouter(g2);
    });
  }

  /**
   *
   */
  private void initDijkstraPaths() {
    this.initializeRedDijkstra();
    this.initializeBlueDijkstra();
  }
  
  /**
   * 
   */
  private void initializeRedDijkstra() {
    /* Start by instantiating the red vertices. */
    Vertex R_start = new Vertex("S", 100, 50); // 1
    Vertex R_R1 = new Vertex("R1", 350, 650);//2
    Vertex R_R2 = new Vertex("R2", 350, 50);//3
    Vertex R_R3 = new Vertex("R3", 1016, 50);//3
    Vertex R_R4 = new Vertex("R4", 1016, 650);//3
    Vertex R_R5 = new Vertex("R5", 683, 325);//3
    Vertex R_H1 = new Vertex("H1", 100, 650);//1
    Vertex R_H2 = new Vertex("H2", 1266, 650);//
    Vertex R_endDest = new Vertex("F", 1266, 50);//5

    /* Add the router image. */
    this.routers.add(new Router(R_R1, this));
    this.routers.add(new Router(R_R2, this));
    this.routers.add(new Router(R_R3, this));
    this.routers.add(new Router(R_R4, this));
    this.routers.add(new Router(R_R5, this));

    /* Adds the edges between the vertices. */
    this.redPathDijkstra.addEdge(R_start, R_H1, 0, true);
    this.redPathDijkstra.addEdge(R_H1, R_R1, 5, true);
    this.redPathDijkstra.addEdge(R_R1, R_R2, 3, true);
    this.redPathDijkstra.addEdge(R_R1, R_R5, 1, true);
    this.redPathDijkstra.addEdge(R_R1, R_R4, 15, true);
    this.redPathDijkstra.addEdge(R_R2, R_R3, 4, true);
    this.redPathDijkstra.addEdge(R_R2, R_R5, 1, true);
    this.redPathDijkstra.addEdge(R_R3, R_R5, 8, true);
    this.redPathDijkstra.addEdge(R_R3, R_R4, 7, true);
    this.redPathDijkstra.addEdge(R_R5, R_R4, 25, true);
    this.redPathDijkstra.addEdge(R_R4, R_H2, 3, true);
    this.redPathDijkstra.addEdge(R_H2, R_endDest, 0, true);
    
    this.redPathDijkstra.dijkstra(R_start);
    Simulator.redPath = this.redPathDijkstra.getDijkstraPath(R_endDest);
  }
  
  /**
   * 
   */
  private void initializeBlueDijkstra() {
    /* Initialize the blue vertices. */
    Vertex B_start = new Vertex("S", 100, 50); // 1
    Vertex B_R1 = new Vertex("R1", 350, 650);//2
    Vertex B_R2 = new Vertex("R2", 350, 50);//3
    Vertex B_R3 = new Vertex("R3", 1016, 50);//3
    Vertex B_R4 = new Vertex("R4", 1016, 650);//3
    Vertex B_R5 = new Vertex("R5", 683, 325);//3
    Vertex B_H1 = new Vertex("H1", 100, 650);//1
    Vertex B_H2 = new Vertex("H2", 1266, 650);//
    Vertex B_endDest = new Vertex("F", 1266, 50);//5

    /* Initializes the blue edges. */
    this.bluePathDijkstra.addEdge(B_start, B_H1, 0, true);
    this.bluePathDijkstra.addEdge(B_H1, B_R1, 5, true);
    this.bluePathDijkstra.addEdge(B_R1, B_R2, 3, true);
    this.bluePathDijkstra.addEdge(B_R1, B_R5, 30, true);
    this.bluePathDijkstra.addEdge(B_R1, B_R4, 25, true);
    this.bluePathDijkstra.addEdge(B_R2, B_R3, 18, true);
    this.bluePathDijkstra.addEdge(B_R2, B_R5, 5, true);
    this.bluePathDijkstra.addEdge(B_R3, B_R5, 12, true);
    this.bluePathDijkstra.addEdge(B_R3, B_R4, 3, true);
    this.bluePathDijkstra.addEdge(B_R5, B_R4, 14, true);
    this.bluePathDijkstra.addEdge(B_R4, B_H2, 3, true);
    this.bluePathDijkstra.addEdge(B_H2, B_endDest, 0, true);

    this.bluePathDijkstra.dijkstra(B_start);
    Simulator.bluePath = this.bluePathDijkstra.getDijkstraPath(B_endDest);
  }
  
  /**
   * 
   * @param g2
   * @param d
   * @param weightColor
   */
  private void drawDijkstraPath(Graphics2D g2, Dijkstra d, Color weightColor) {
    int EDGE_OFFSET = 10;
    int V_TEXT_Y_OFFSET = 0;
    
    Iterator<Vertex> it;
    if (weightColor == Color.RED) {
      it = this.redPathDijkstra.getVertices().iterator();
    } else if (weightColor == Color.BLUE) {
      it = this.bluePathDijkstra.getVertices().iterator();
      V_TEXT_Y_OFFSET = 20; // Slightly lower offset for blue edges.
    } else {
      throw new IllegalArgumentException("Only colors available are RED and BLUE.");
    }

    /* Iterate through the vertices and draw the associated edges. */
    while (it.hasNext()) {
      Vertex v = it.next();
      for (Edge e : v.adjacencyList) {

        /* Draws an edge itself. */
        g2.setColor(Color.BLACK);
        g2.drawLine(e.source.getX() + EDGE_OFFSET,
                e.source.getY() + EDGE_OFFSET,
                e.destination.getX() + EDGE_OFFSET,
                e.destination.getY() + EDGE_OFFSET);

        /* Draws the edge weight. */
        g2.setColor(weightColor);
        g2.drawString(String.format("%.1f", e.distance),
                (int) ((e.source.getX() + e.destination.getX()) / 2),
                (int) ((e.source.getY() + e.destination.getY()) / 2 + V_TEXT_Y_OFFSET));
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
    this.redBall = new Ball(this, 20, 20, 2, 0, Color.RED, redData);
    this.blueBall = new Ball(this, 60, 60, 2, 0, Color.BLUE, blueData);
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
  
  public boolean isDrawingTrails() {
    return this.isDrawingTrails;
  }
  
  public void setDrawingTrails(boolean isDrawingTrails) {
    this.isDrawingTrails = isDrawingTrails;
  }

  public Ball getRedBall() {
    return redBall;
  }

  public Ball getBlueBall() {
    return blueBall;
  }
}
