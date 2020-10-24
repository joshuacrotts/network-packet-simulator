//=============================================================================================//
// FILENAME :       Router.java
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
// AUTHOR   :   Joshua Crotts        START DATE :    28 Aug. 2020
// CLASS    :   CSC - 677 
// SEMESTER :   FALL 2020
//
//=============================================================================================//
package com.joshuacrotts.uncg.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.joshuacrotts.uncg.NetworkUtils;
import com.joshuacrotts.uncg.Simulator;
import com.joshuacrotts.uncg.StdOps;
import com.joshuacrotts.uncg.dijkstra.Vertex;

public class Router implements MouseListener {

  // The parent simulator is passed so we can add the MouseListener to the panel.
  private final Simulator simulator;

  // Each router has an underlying vertex used for its position.
  private final Vertex parentVertex;
  private BufferedImage routerImage;

  // Dimension information.
  private static final int ROUTER_WIDTH = 80;
  private static final int ROUTER_HEIGHT = 45;

  // Active or not for each ball.
  private boolean isRedActive;
  private boolean isBlueActive;
  
  public Router(Vertex parent, Simulator simulator) {
    this.parentVertex = parent;
    this.simulator = simulator;

    try {
      this.routerImage = ImageIO.read(Router.class.getResourceAsStream("/router.png"));
    } catch (IOException ex) {
      Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
      System.exit(1);
    }

    this.simulator.addMouseListener(this);
  }

  /**
   * 
   */
  public void updateRouter() {
    // No reason to continue if both are active.
    if (this.isBlueActive && this.isRedActive) {
      return;
    }
    
    // Check to see if we're close to the router (coordinates).
    double redX = this.simulator.getRedBall().getX();
    double redY = this.simulator.getRedBall().getY();
    double blueX = this.simulator.getBlueBall().getX();
    double blueY = this.simulator.getBlueBall().getY();
    double rX = this.parentVertex.getX();
    double rY = this.parentVertex.getY();
    int threshold = 5;
    
    // First we check the red ball.
    boolean isRXClose = StdOps.isDoubleEqual(redX, rX, threshold);
    boolean isRYClose = StdOps.isDoubleEqual(redY, rY, threshold);
    
    if (!this.isRedActive && isRXClose && isRYClose) {
      this.isRedActive = true;
    }
    
    // Now check the blue ball.
    boolean isBXClose = StdOps.isDoubleEqual(blueX, rX, threshold);
    boolean isBYClose = StdOps.isDoubleEqual(blueY, rY, threshold);
    
    if (!this.isBlueActive && isBXClose && isBYClose) {
      this.isBlueActive = true;
    }
  }

  /**
   *
   * @param g2
   */
  public void drawRouter(Graphics2D g2) {
    g2.drawImage(this.routerImage,
            this.parentVertex.getX() - ROUTER_WIDTH / 2,
            this.parentVertex.getY(), ROUTER_WIDTH,
            ROUTER_HEIGHT, null);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (StdOps.isMouseOver(e.getX(), e.getY(),
            this.parentVertex.getX() - ROUTER_WIDTH / 2,
            this.parentVertex.getY(), ROUTER_WIDTH, ROUTER_HEIGHT)) {
      this.simulator.setPaused(true);

      if (this.isRedActive || this.isBlueActive) {
        if (this.isRedActive) {
          String data = this.simulator.getRedBall().getNetworkData().frame;
          System.out.println("Data: " + data);
          openRouterJOptionPane("Red", data);
        }
        
        if (this.isBlueActive) {
          String data = this.simulator.getBlueBall().getNetworkData().frame;
          openRouterJOptionPane("Blue", data);
        }
      } else {
        JOptionPane.showMessageDialog(this.simulator, "Router " + this.parentVertex.getID());
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent _e) {
  }

  @Override
  public void mouseReleased(MouseEvent _e) {
  }

  @Override
  public void mouseEntered(MouseEvent _e) {
  }

  @Override
  public void mouseExited(MouseEvent _e) {
  }

  /**
   * 
   * @param data 
   */
  private void openRouterJOptionPane(String ballColor, String data) {
    JTextArea textArea = new JTextArea(NetworkUtils.convertHexStrToBinaryStr(data));
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    textArea.setWrapStyleWord(true);
    scrollPane.setPreferredSize(new Dimension(250, 100));
    JOptionPane.showMessageDialog(this.simulator, scrollPane, ballColor + " Ball Data: ", JOptionPane.DEFAULT_OPTION);
  }
}
