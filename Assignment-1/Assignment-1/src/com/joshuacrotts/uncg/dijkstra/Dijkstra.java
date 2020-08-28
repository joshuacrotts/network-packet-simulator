package com.joshuacrotts.uncg.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author joshuacrotts
 */
public class Dijkstra {

  public static final Set<Vertex> vertices = new HashSet<>();
  
  /**
   * 
   * @param source 
   */
  public void dijkstra(Vertex source) {
    source.distanceFromSource = 0;

    PriorityQueue<Vertex> heap = new PriorityQueue<>();
    heap.add(source);

    while (!heap.isEmpty()) {
      Vertex u = heap.poll();

      /* Visit all adjacencies. */
      for (Edge e : u.adjacencyList) {
        Vertex v = e.destination;
        double edgeWeight = e.distance;

        double distanceFromU = u.distanceFromSource + edgeWeight;
        if (distanceFromU < v.distanceFromSource) {
          heap.remove(u);
          v.distanceFromSource = distanceFromU;
          v.previousVertex = u;
          heap.add(v);
        }
      }
    }
  }

  /**
   * @param target
   * @return 
   */
  public List<Vertex> getDijkstraPath(Vertex target) {

    List<Vertex> path = new ArrayList<>();
    
    /* If there is no path, just return nothing. */
    if (target.previousVertex == null) {
      return path;
    }
    
    /* Otherwise, construct the path backwards, then reverse it. */
    for (Vertex v = target; v != null; v = v.previousVertex) {
      path.add(v);
    }

    Collections.reverse(path);
    return path;
  }
  
  public static void addEdge(Vertex src, Vertex dest) {
    Dijkstra.vertices.add(src);
    Dijkstra.vertices.add(dest);
    
    Edge e = new Edge(src, dest);
  }
}
