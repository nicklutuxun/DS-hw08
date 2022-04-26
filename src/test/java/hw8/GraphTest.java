package hw8;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import javafx.geometry.Pos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class GraphTest {

  protected Graph<String, String> graph;

  @BeforeEach
  public void setupGraph() {
    this.graph = createGraph();
  }

  protected abstract Graph<String, String> createGraph();

  @Test
  @DisplayName("insert(v) returns a vertex with given data")
  public void canGetVertexAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals(v1.get(), "v1");
  }

  @Test
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void canGetEdgeAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenfirstVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(null, v, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  // TODO add more tests here.
  @Test
  @DisplayName("remove null node throws exception")
  public void removeNullNodeThrowsInsertionException() {
    try {
      graph.remove((Vertex<String>) null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }
  
  @Test
  @DisplayName("remove node with incident edges throws exception")
  public void removeNodeWithIncidentEdgesThrowsRemovalException() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }
  
  @Test
  @DisplayName("vertices() correctly returns an iterable of all vertices")
  public void verticesCorrectlyReturnsAnIterableOfVertices() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    Edge<String> e3 = graph.insert(v3, v1, "v3-v1");
    int counter = 0;
    for (Vertex<String> v : graph.vertices()) {
      counter++;
    }
    assertEquals(counter, 3);
  }
  
  @Test
  @DisplayName("edges() correctly returns an iterable of all edges")
  public void EdgesCorrectlyReturnsAnIterableOfEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    Edge<String> e3 = graph.insert(v3, v1, "v3-v1");
    int counter = 0;
    for (Edge<String> e : graph.edges()) {
      counter++;
    }
    assertEquals(counter, 3);
  }
}
