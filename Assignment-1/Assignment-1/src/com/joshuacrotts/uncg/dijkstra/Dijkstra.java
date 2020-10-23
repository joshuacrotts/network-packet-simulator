package com.joshuacrotts.uncg.dijkstra;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

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

    while ( ! heap.isEmpty()) {
      Vertex u = heap.poll();

      /*
       * Visit all adjacencies.
       */
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
  public Stack<Vertex> getDijkstraPath(Vertex target) {
    Stack<Vertex> path = new Stack<>();

    /*
     * If there is no path, just return nothing.
     */
    if (target.previousVertex == null) {
      return path;
    }

    /*
     * Otherwise, construct the path backwards, then reverse it. We use a stack
     * because of its natural reversed order.
     */
    for (Vertex v = target; v != null; v = v.previousVertex) {
      path.add(v);
    }

    return path;
  }

  /**
   * 
   * @param src
   * @param dest 
   */
  public static void addEdge(Vertex src, Vertex dest, double distance, boolean isUndirected) {
    Dijkstra.vertices.add(src);
    Dijkstra.vertices.add(dest);

    Edge e = new Edge(src, dest, distance);
    if (isUndirected) {
      e = new Edge(dest, src, distance);
    }
  }
  
  /**
   * 
   * @param src
   * @param dest 
   */
  public static void addEdge(Vertex src, Vertex dest) {
    Dijkstra.vertices.add(src);
    Dijkstra.vertices.add(dest);

    Edge e = new Edge(src, dest);
  }
}
