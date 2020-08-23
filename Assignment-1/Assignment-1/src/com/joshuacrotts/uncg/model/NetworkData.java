/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joshuacrotts.uncg.model;

import javax.swing.JOptionPane;

/**
 *
 * @author Joshua
 */
public class NetworkData {

  private final String message;

  public NetworkData(String message) {
    if (message == null || message.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Error, please enter a message!", "ERROR", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

}
