package hw8;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Position;
import hw8.graph.Vertex;
import javafx.geometry.Pos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
  @DisplayName("insert duplicate vertices throws exception")
  public void insertDuplicateVerticesThrowsInsertionException () {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }
  
  @Test
  @DisplayName("insert duplicate edges throws exception")
  public void insertDuplicateEdgesThrowsInsertionException () {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      graph.insert(v1, v2, "v1-v2");
      graph.insert(v1, v2, "v1-v2");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }
  
  @Test
  @DisplayName("insert edges that result in self-loop throws exception")
  public void insertEdgesSelfLoopThrowsInsertionException () {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      graph.insert(v1, v1, "v1-v1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
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
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void getEndpointsOfAnEdgeFromADifferentGraph() {
    Graph<String, String> graph2 = createGraph();
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph2.insert("v2");
    Vertex<String> v3 = graph2.insert("v3");
    Edge<String> e1 = graph2.insert(v2, v3, "v1-v2");
    try {
      graph.from(e1);
      graph.to(e1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenFirstVertexIsNull() {
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
      System.out.println(v.get());
      counter++;
    }
    assertEquals(counter, 3);
  }
  
  @Test
  @DisplayName("vertices() throws exception when try to modify content")
  public void verticesThrowsExceptionWhenTryToModifyContent() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    Edge<String> e3 = graph.insert(v3, v1, "v3-v1");
    Set<Vertex<String>> vertices = (Set<Vertex<String>>) graph.vertices();
    try {
      vertices.remove(v1);
      fail("The expected exception was not thrown");
    } catch (UnsupportedOperationException ex) {
      return;
    }
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
      System.out.println(e.get());
      counter++;
    }
    assertEquals(counter, 3);
  }
  
  @Test
  @DisplayName("edge() throws exception when try to modify content")
  public void edgesThrowsExceptionWhenTryToModifyContent() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    Edge<String> e3 = graph.insert(v3, v1, "v3-v1");
    Set<Edge<String>> edges = (Set<Edge<String>>) graph.edges();
    try {
      edges.remove(e1);
      fail("The expected exception was not thrown");
    } catch (UnsupportedOperationException ex) {
      return;
    }
  }
  
  @Test
  @DisplayName("outgoing() correctly returns an iterable of all outgoing edges")
  public void outgoingCorrectlyReturnsAnIterableOfOutgoingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    Edge<String> e3 = graph.insert(v3, v1, "v3-v1");
    int counter = 0;
    for (Edge<String> e : graph.outgoing(v1)) {
      System.out.println(e.get());
      counter++;
    }
    assertEquals(counter, 1);
  }
  
  @Test
  @DisplayName("incoming() correctly returns an iterable of all incoming edges")
  public void incomingCorrectlyReturnsAnIterableOfIncomingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    Edge<String> e3 = graph.insert(v3, v1, "v3-v1");
    int counter = 0;
    for (Edge<String> e : graph.incoming(v1)) {
      System.out.println(e.get());
      counter++;
    }
    assertEquals(counter, 1);
  }
  
  @Test
  @DisplayName("clearLabels() correctly clear all labels")
  public void clearLabelsCorrectlyBehaves() {
    Vertex<String> v1 = graph.insert("v1");
    graph.label(v1, "1");
    Vertex<String> v2 = graph.insert("v2");
    graph.label(v2, "2");
    Vertex<String> v3 = graph.insert("v3");
    graph.label(v3, "3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    graph.label(e1, "1");
    Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    graph.label(e2, "2");
    Edge<String> e3 = graph.insert(v3, v1, "v3-v1");
    graph.label(e3, "3");
    
    graph.clearLabels();
    for (Vertex<String> v : graph.vertices()) {
      assertNull(graph.label(v));
    }
    for (Edge<String> e : graph.edges()) {
      assertNull(graph.label(e));
    }
  }
  
  @Test
  @DisplayName("check graphviz")
  public void checkGraphviz() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    Edge<String> e3 = graph.insert(v3, v1, "v3-v1");
    System.out.println(graph);
  }
}
