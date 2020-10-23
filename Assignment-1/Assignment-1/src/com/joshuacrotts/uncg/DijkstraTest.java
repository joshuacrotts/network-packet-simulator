package com.joshuacrotts.uncg;

import com.joshuacrotts.uncg.dijkstra.Dijkstra;
import com.joshuacrotts.uncg.dijkstra.Vertex;
import java.util.Stack;

/**
 *
 * @author Joshua
 */
public class DijkstraTest {

  public static void main(String[] args) {

    /* Instantiates the vertices with a string ID (just for naming), and position. */
    Vertex A = new Vertex("A", 100, 100);//1
    Vertex B = new Vertex("B", 100, 400);//2
    Vertex C = new Vertex("C", 300, 400);//3
    Vertex D = new Vertex("D", 450, 200);//4
    Vertex E = new Vertex("E", 450, 600);//5
    Vertex F = new Vertex("F", 650, 200);//6
    Vertex G = new Vertex("G", 650, 600);//7
    Vertex H = new Vertex("H", 550, 400);//8
    Vertex I = new Vertex("I", 750, 400);//19
    Vertex J = new Vertex("J", 750, 200);//10
    Vertex K = new Vertex("K", 750, 100);//11
    Vertex L = new Vertex("L", 750, 0);//12

    /* Adds the edges between the vertices. All this does is assign the adjacency
       list values. */
    Dijkstra.addEdge(C, E, true);
    Dijkstra.addEdge(A, B, true);
    Dijkstra.addEdge(B, C, true);
    Dijkstra.addEdge(C, D, true);
    Dijkstra.addEdge(D, E, true);
    Dijkstra.addEdge(D, F, true);
    Dijkstra.addEdge(F, E, true);
    Dijkstra.addEdge(E, G, true);
    Dijkstra.addEdge(F, H, true);
    Dijkstra.addEdge(G, H, true);
    Dijkstra.addEdge(H, I, true);
    Dijkstra.addEdge(I, J, true);
    Dijkstra.addEdge(J, K, true);
    Dijkstra.addEdge(K, L, true);

    Dijkstra d = new Dijkstra();

    d.dijkstra(A);

    /* Prints the adjacency lists of each node. */
//    System.out.println(A.adjacencyList);
//    System.out.println(B.adjacencyList);
//    System.out.println(C.adjacencyList);
//    System.out.println(D.adjacencyList);
//    System.out.println(E.adjacencyList);
//    System.out.println(F.adjacencyList);
//    System.out.println(G.adjacencyList);
//    System.out.println(H.adjacencyList);
//    System.out.println(I.adjacencyList);
//    System.out.println(J.adjacencyList);
//    System.out.println(K.adjacencyList);
//    System.out.println(L.adjacencyList);
    Stack<Vertex> p = d.getDijkstraPath(L);
    
    while(!p.isEmpty()) {
      System.out.println(p.pop());
    }
  }
}
