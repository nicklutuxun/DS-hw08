package hw8.spp;

import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraStreetSearcher extends StreetSearcher {
  
  private final HashMap<Vertex<String>, Double> dist = new HashMap<>();
  
  /**
   * Creates a StreetSearcher object.
   *
   * @param graph an implementation of Graph ADT.
   */
  public DijkstraStreetSearcher(Graph<String, String> graph) {
    super(graph);
  }
  
  @Override
  public void findShortestPath(String startName, String endName) {
    // whether vertex is explored
    HashMap<Vertex<String>, Boolean> explore = new HashMap<>();
    // head is always a vertex with the shortest distance from start to it
    PriorityQueue<Vertex<String>> pq = new PriorityQueue<>(new VertexComparator());
    
    // TODO - Implement Dijkstra Algorithm!
    // set up
    for (Vertex<String> v : graph.vertices()) {
      dist.put(v, MAX_DISTANCE);
      explore.put(v, false);
    }
    
    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);
    pq.add(start);
    dist.replace(start, 0.0);
    
    // run dijkstra
    dijkstra(pq, explore, end);
    
    // trace back from end
    double totalDist = dist.get(end);
    // These method calls will create and print the path for you
    List<Edge<String>> path = getPath(end, start);
    if (VERBOSE) {
      printPath(path, totalDist);
    }
  }
  
  private void dijkstra(PriorityQueue<Vertex<String>> pq, HashMap<Vertex<String>,
      Boolean> explore, Vertex<String> end) {
    // continue when pq is not empty and stop when end is found
    while (!pq.isEmpty() && !explore.get(end)) {
      Vertex<String> v = pq.remove();
      explore.replace(v, true);
      for (Edge<String> e : graph.outgoing(v)) {
        Vertex<String> u = graph.to(e);
        // get unexplored neighbor
        if (explore.get(u)) {
          continue;
        }
        Edge<String> edge = getEdge(v, u);
        Double d = dist.get(v) + (Double) graph.label(edge);
        if (d < dist.get(u)) {
          dist.replace(u, d);
          graph.label(u, e);
          pq.add(u);
        }
      }
    }
  }
  
  private Edge<String> getEdge(Vertex<String> v, Vertex<String> u) {
    for (Edge<String> e : graph.outgoing(v)) {
      if (graph.to(e) == u) {
        return e;
      }
    }
    return null;
  }
  
  protected class VertexComparator implements Comparator<Vertex<String>> {
    
    @Override
    public int compare(Vertex<String> v1, Vertex<String> v2) {
      return Integer.compare(dist.get(v1).compareTo(dist.get(v2)), 0);
    }
  }
}