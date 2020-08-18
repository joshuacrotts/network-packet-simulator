package com.joshuacrotts.uncg.controller;

import com.joshuacrotts.uncg.view.NetworkView;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joshuacrotts
 */
public class NetworkController implements Runnable {

  /*
   * Model and view objects according to my standard.
   */
  private final NetworkView view;

  /*
   * Thread for the animation loop/actions.
   */
  private Thread thread;

  /*
   * Thread information.
   */
  private final int FRAMES_PER_SECOND = 60;
  private boolean isRunning = false;

  /*
   * Positioning and title of the view.
   */
  private final String TITLE = "Network Simulator Assignment 1";
  private final int WIDTH = 800;
  private final int HEIGHT = 600;

  public NetworkController() {
    this.view = new NetworkView(WIDTH, HEIGHT, TITLE);

    this.startController();
  }

  /**
   *
   * @Override default runnable procedure in Runnable interface.
   */
  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(FRAMES_PER_SECOND);
      } catch (InterruptedException ex) {
        Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   *
   */
  private synchronized void startController() {
    if (this.isRunning) {
      return;
    }

    this.thread = new Thread(this);
    this.thread.start();
    this.isRunning = true;
  }

  /**
   *
   */
  private synchronized void stopController() {
    if ( ! this.isRunning) {
      return;
    }

    try {
      this.thread.join();
    } catch (InterruptedException ex) {
      Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
