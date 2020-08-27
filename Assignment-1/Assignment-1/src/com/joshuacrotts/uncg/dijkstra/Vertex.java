/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joshuacrotts.uncg.dijkstra;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author joshuacrotts
 */
public class Vertex implements Comparable<Vertex> {
  protected String id;
  protected int x;
  protected int y;
  
  protected boolean visited;
  protected double distanceFromSource;
  protected Vertex previousVertex;
  protected List<Edge> adjacencyList;
  
  public Vertex(String id, int x, int y) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.previousVertex = null;
    this.adjacencyList = new LinkedList<>();
  }
  
  public void addEdge(Edge edge) {
    this.adjacencyList.add(edge);
  }
  
  @Override
  public int compareTo(Vertex v) {
    return Double.compare(v.distanceFromSource, this.distanceFromSource);
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    
    if (this == o) {
      return true;
    }
    
    Vertex otherVertex = (Vertex) o;
    return this.id.equals(otherVertex.id) && this.x == otherVertex.x && this.y == otherVertex.y;
  }
  
  @Override
  public String toString() {
    return this.id + ": (" + this.x + ", " + this.y + ")";
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public String getID() {
    return this.id;
  }
  
}