/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joshuacrotts.uncg.controller;

import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import java.awt.Color;

/**
 *
 * @author joshuacrotts
 */
public class NetworkController extends StandardGame {

  public NetworkController() {
    super(800, 600, "Network Simulator - Assignment 1");
    
    this.startGame();
  }

  @Override
  public void tick() {
  }

  @Override
  public void render() {
    StandardDraw.drawEllipse(20,20, 300, 300, Color.yellow, true);
  }
}
