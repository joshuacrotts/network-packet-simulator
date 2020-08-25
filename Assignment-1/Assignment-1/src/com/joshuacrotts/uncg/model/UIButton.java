//=============================================================================================//
// FILENAME :       UIButton.java
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

import com.joshuacrotts.uncg.Simulator;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class UIButton extends JButton {

  private final Simulator simulator;

  private final int MAX_WIDTH = 100;
  private final int MAX_HEIGHT = 35;

  public UIButton(Simulator simulator, String text, String buttonBackgroundFile) {
    super(text);
    
    this.simulator = simulator;
    URL imageURL = getClass().getResource(buttonBackgroundFile);

    if (imageURL != null) {
      /* Assigns the new button texture as a background. */
      super.setIcon(new ImageIcon("res" + buttonBackgroundFile));
      super.setHorizontalTextPosition(JButton.CENTER);
      super.setVerticalTextPosition(JButton.CENTER);
      super.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));

      /* Sets the default JButton texture to effectively empty. */
      super.setOpaque(false);
      super.setFocusPainted(false);
      super.setBorderPainted(true);
      super.setContentAreaFilled(false);
      super.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

      /* Adds the listener to the button component. */
    } else {
      System.err.println("Error, could not find background for JButton. File passed is " + buttonBackgroundFile + ".");
      System.exit(1);
    }
  }

  public Simulator getSimulator() {
    return simulator;
  }
}
