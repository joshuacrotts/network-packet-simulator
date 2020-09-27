package com.joshuacrotts.uncg.dijkstra;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author joshuacrotts
 */
public class Vertex implements Comparable<Vertex> {

  protected String id;
  protected int x;
  protected int y;

  protected boolean visited;
  protected double distanceFromSource = Double.MAX_VALUE;
  protected Vertex previousVertex;
  
  public  List<Edge> adjacencyList;

  public Vertex(String id, int x, int y) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.previousVertex = null;
    this.adjacencyList = new LinkedList<>();
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
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + Objects.hashCode(this.id);
    hash = 59 * hash + this.x;
    hash = 59 * hash + this.y;
    return hash;
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
