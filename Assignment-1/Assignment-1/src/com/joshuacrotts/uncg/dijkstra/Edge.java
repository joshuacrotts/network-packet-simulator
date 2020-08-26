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
  protected Vertex source;
  protected Vertex destination;
  
  protected double distance;
  
  public Edge(Vertex src, Vertex dest) {
    this.source = src;
    this.destination = dest;
    
    this.distance = Edge.computeDistance(src, dest);
    System.out.println(this);
  }
  
  private static double computeDistance(Vertex src, Vertex dest) {
    return Math.sqrt((src.x - dest.x) * (src.x - dest.x) + (src.y - dest.y) * (src.y - dest.y));
  }
  
  @Override
  public String toString() {
    return "Source: " + source + " ---> Destination: " + destination + " Distance: " + this.distance;
  }
}
