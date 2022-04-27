package hw8.graph;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  // TODO You may need to add fields/constructor here!
  HashMap<Vertex<V>, HashSet<Edge<E>>> incoming = new HashMap<>();
  HashMap<Vertex<V>, HashSet<Edge<E>>> outgoing = new HashMap<>();
  HashSet<Vertex<V>> allVertices = new HashSet<>();
  HashSet<Edge<E>> allEdges = new HashSet<>();

  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts and edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    // TODO Implement me!
    if (v == null) {
      throw new InsertionException();
    }
    
    VertexNode<V> newVertexNode = new VertexNode(v);
    
    if (hasDuplicateVertex(v)) {
      throw new InsertionException();
    }
  
    newVertexNode.owner = this;
    HashSet<Edge<E>> incomingEdges = new HashSet<>();
    HashSet<Edge<E>> outgoingEdges = new HashSet<>();
    incoming.put(newVertexNode, incomingEdges);
    outgoing.put(newVertexNode, outgoingEdges);
    allVertices.add(newVertexNode);
    return newVertexNode;
  }
  
  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {
    // TODO Implement me!
    if (from == null || to == null) {
      throw new PositionException();
    }
  
    // check self-loop
    if (from == to) {
      throw new InsertionException();
    }
    
    // not in graph
    if (!allVertices.contains(from) || !allVertices.contains(to)) {
      throw new PositionException();
    }
    
    VertexNode<V> gvFrom = convert(from);
    VertexNode<V> gvTo = convert(to);
    
    if (hasDuplicateEdge(gvFrom, gvTo)) {
      throw new InsertionException();
    }
    
    EdgeNode<E> newEdgeNode = new EdgeNode(gvFrom, gvTo, e);
    newEdgeNode.owner = this;
    outgoing.get(gvFrom).add(newEdgeNode);
    incoming.get(gvTo).add(newEdgeNode);
    allEdges.add(newEdgeNode);
    return newEdgeNode;
  }
  
  private boolean hasDuplicateVertex(V v) {
    for (Vertex<V> vertex : allVertices) {
      VertexNode<V> gv = convert(vertex);
      if (gv.data == v) {
        return true;
      }
    }
    return false;
  }
  
  private boolean hasDuplicateEdge(VertexNode<V> gvFrom, VertexNode<V> gvTo) {
    for (Edge<E> e : outgoing.get(gvFrom)) {
      EdgeNode<E> ge = convert(e);
      if (ge.to == gvTo) {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    // TODO Implement me!
    VertexNode<V> gv = convert(v);
    HashSet<Edge<E>> outgoingEdges = outgoing.get(gv);
    HashSet<Edge<E>> incomingEdges = incoming.get(gv);
    
    if (!outgoingEdges.isEmpty() || !incomingEdges.isEmpty()) {
      throw new RemovalException();
    }
    
    outgoing.remove(gv);
    incoming.remove(gv);
    allVertices.remove(gv);
    return gv.data;
  }

  @Override
  public E remove(Edge<E> e) throws PositionException {
    // TODO Implement me!
    EdgeNode<E> ge = convert(e);
    VertexNode<V> from = ge.from;
    outgoing.get(from).remove(ge);
    VertexNode<V> to = ge.to;
    incoming.get(to).remove(ge);
    allEdges.remove(ge);
    return ge.data;
  }

  @Override
  public Iterable<Vertex<V>> vertices() {
    // TODO Implement me!
    return allVertices;
  }

  @Override
  public Iterable<Edge<E>> edges() {
    // TODO Implement me!
    return allEdges;
  }

  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    // TODO Implement me!
    return outgoing.get(v);
  }

  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    // TODO Implement me!
    return incoming.get(v);
  }

  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    // TODO Implement me!
    EdgeNode<E> ge = convert(e);
    return ge.from;
  }

  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    // TODO Implement me!
    EdgeNode<E> ge = convert(e);
    return ge.to;
  }

  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    // TODO Implement me!
    VertexNode gv = convert(v);
    gv.label = l;
  }

  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    // TODO Implement me!
    EdgeNode ge = convert(e);
    ge.label = l;
  }

  @Override
  public Object label(Vertex<V> v) throws PositionException {
    // TODO Implement me!
    VertexNode gv = convert(v);
    return gv.label;
  }

  @Override
  public Object label(Edge<E> e) throws PositionException {
    // TODO Implement me!
    EdgeNode ge = convert(e);
    return ge.label;
  }

  @Override
  public void clearLabels() {
    // TODO Implement me!
    for (Vertex<V> v : vertices()) {
      VertexNode gv = convert(v);
      gv.label = null;
    }
  
    for (Edge<E> e : edges()) {
      EdgeNode ge = convert(e);
      ge.label = null;
    }
    
  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<>(this);
    return gp.toString();
  }

  // Class for a vertex of type V
  private final class VertexNode<V> implements Vertex<V> {
    V data;
    Graph<V, E> owner;
    Object label;
    // TODO You may need to add fields/methods here!

    VertexNode(V v) {
      this.data = v;
      this.label = null;
    }

    @Override
    public V get() {
      return this.data;
    }
  }

  //Class for an edge of type E
  private final class EdgeNode<E> implements Edge<E> {
    E data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;
    // TODO You may need to add fields/methods here!

    // Constructor for a new edge
    EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
    }

    @Override
    public E get() {
      return this.data;
    }
    
  }
}
