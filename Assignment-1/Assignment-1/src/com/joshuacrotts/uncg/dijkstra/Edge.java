/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joshuacrotts.uncg.dijkstra;

/**
 *
 * @author joshuacrotts
 */
public class Edge {

  public Vertex source;
  public Vertex destination;
  
  public double distance;

  protected Edge(Vertex src, Vertex dest) {
    this.source = src;
    this.destination = dest;

    this.distance = Edge.computeDistance(src, dest);
    src.adjacencyList.add(this);
  }
  
  protected Edge(Vertex src, Vertex dest, double distance) {
    this.source = src; 
    this.destination = dest;
    this.distance = distance;
    src.adjacencyList.add(this);
  }

  /**
   * 
   * @param src
   * @param dest
   * @return 
   */
  private static double computeDistance(Vertex src, Vertex dest) {
    return Math.sqrt((src.x - dest.x) * (src.x - dest.x) + (src.y - dest.y) * (src.y - dest.y));
  }

  @Override
  public String toString() {
    return "Destination: " + destination + ", distance: " + distance;
  }
}
