/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joshuacrotts.uncg.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author joshuacrotts
 */
public class Dijkstra {

  private final Map<String, Vertex> vertices;

  public Dijkstra() {
    this.vertices = new HashMap<>();
  }

  public void addVertex(Vertex v) {
    if (vertices.containsKey(v.id)) {
      throw new IllegalArgumentException("Error, this vertex already exists: " + v);
    }

    this.vertices.put(v.id, v);
  }

  public void addEdge(String src, String dest) {
    if ( ! vertices.containsKey(src)) {
      throw new IllegalArgumentException("Error, src does not exist in the map.");
    }

    if ( ! vertices.containsKey(dest)) {
      throw new IllegalArgumentException("Error, dest does not exist in the map.");
    }

    Vertex srcVertex = this.vertices.get(src);
    Vertex destVertex = this.vertices.get(dest);

    Edge e = new Edge(srcVertex, destVertex);

    srcVertex.addEdge(e);
  }

  /**
   * COPIED CHANGE IT!!!!! !!! !!! !!! !!! !! !!!*
   */
  private void dijkstra(String s) {

    List<String> remainingVertices = new ArrayList<>();
    
    /* Add each vertex to the list of remaining ones that we need to perform
       dijkstra on. */
    this.vertices.values().forEach((u) -> {
      remainingVertices.add(u.id);
    });
    
    int checkIndex = remainingVertices.indexOf(s);
    Vertex checking = this.vertices.get(remainingVertices.remove(checkIndex));
    checking.distanceFromSource = 0;

    /* Iterate through the vertices and max out their distance from the source. */
    for (int i = 0; i < remainingVertices.size(); i ++) {
      Vertex v = this.vertices.get(remainingVertices.get(i));
      v.distanceFromSource = Integer.MAX_VALUE;
    }

    /* Now, go through the vertex and assign the previous distances and pointers. */
    for (Edge e : checking.adjacencyList) {
      Vertex target = e.destination;
      int idxOfModifying = remainingVertices.indexOf(target.id);
      Vertex modifying = this.vertices.get(remainingVertices.get(idxOfModifying));

      modifying.previousVertex = checking;
      modifying.distanceFromSource = checking.distanceFromSource + e.distance;
    }
    
    while ( ! remainingVertices.isEmpty()) {
      double smallestSoFar = Integer.MAX_VALUE;
      int indexToRemove = 0;
      
      // Find the minimum node thus far.
      for (int i = 0; i < remainingVertices.size(); i ++) {
        Vertex v = vertices.get(remainingVertices.get(i));
        if (v.distanceFromSource < smallestSoFar) {
          smallestSoFar = v.distanceFromSource;
          indexToRemove = i;
        }
      }
      
      checking = vertices.get(remainingVertices.remove(indexToRemove));

      for (Edge e : checking.adjacencyList) {
        Vertex target = e.destination;
        if (remainingVertices.contains(target.id)) {
          Vertex modifying = vertices.get(target.id);
          double tmp = checking.distanceFromSource + e.distance;

          if (modifying.distanceFromSource > tmp) {
            modifying.distanceFromSource = tmp;
            modifying.previousVertex = checking;
          }
        }
      }
    }
  }

  /**
   */
  public List<Vertex> getDijkstraPath(String s, String t) {
    /* Clear all distances from the source and the previous pointers. */
    for (Vertex u : vertices.values()) {
      u.distanceFromSource = Integer.MAX_VALUE;
      u.previousVertex = null;
    }

    this.dijkstra(s);

    /* Create a path of all vertices used in the path. */
    List<Vertex> path = new ArrayList<>();

    Vertex v = vertices.get(t);
    
    while (v.previousVertex != null) {
      path.add(vertices.get(v.id));
      v = v.previousVertex;
    }
    
    /* Now add the starting node. */
    path.add(vertices.get(s));

    /* Reverse the list so we have front-to-end. */
    Collections.reverse(path);
    return path;
  }
}
