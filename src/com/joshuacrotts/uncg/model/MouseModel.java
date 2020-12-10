//=============================================================================================//
// FILENAME :       MouseModel.java
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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseModel implements MouseListener, MouseMotionListener {

  private int mouseX;
  private int mouseY;

  public MouseModel() {
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.mouseX = e.getX();
    this.mouseY = e.getY();
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.mouseX = e.getX();
    this.mouseY = e.getY();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.mouseX = e.getX();
    this.mouseY = e.getY();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    this.mouseX = e.getX();
    this.mouseY = e.getY();
  }

  @Override
  public void mouseExited(MouseEvent e) {
    this.mouseX = e.getX();
    this.mouseY = e.getY();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    this.mouseX = e.getX();
    this.mouseY = e.getY();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    this.mouseX = e.getX();
    this.mouseY = e.getY();
  }

  //====================== ACCESSORS/MUTATORS ============================//
  
  public int getMouseX() {
    return this.mouseX;
  }

  public int getMouseY() {
    return this.mouseY;
  }

}
