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

import com.joshuacrotts.uncg.Simulator;
import com.joshuacrotts.uncg.StdOps;
import com.joshuacrotts.uncg.dijkstra.Vertex;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author joshuacrotts
 */
public class Router implements MouseListener {

  // The parent simulator is passed so we can add the MouseListener to the panel.
  private final Simulator simulator;

  // Each router has an underlying vertex used for its position.
  private final Vertex parentVertex;
  private BufferedImage routerImage;

  // Dimension information.
  private static final int ROUTER_WIDTH = 80;
  private static final int ROUTER_HEIGHT = 45;

  public Router(Vertex parent, Simulator simulator) {
    this.parentVertex = parent;
    this.simulator = simulator;

    try {
      this.routerImage = ImageIO.read(new File("res/router.png"));
    } catch (IOException ex) {
      Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
      System.exit(1);
    }

    this.simulator.addMouseListener(this);
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
      JOptionPane.showMessageDialog(this.simulator, "Router " + this.parentVertex.getID());
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
}
