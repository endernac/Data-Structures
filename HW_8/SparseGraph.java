package hw8;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
    An implementation of a directed graph using incidence lists
    for sparse graphs where most things aren't connected.
    @param <V> Vertex element type.
    @param <E> Edge element type.
*/
public class SparseGraph<V, E> implements Graph<V, E> {

    // Class for a vertex of type V
    private final class VertexNode<V> implements Vertex<V> {
        V data;
        Graph<V, E> owner;
        List<Edge<E>> outgoing;
        List<Edge<E>> incoming;
        Object label;
        double distance;
        boolean visited;

        VertexNode(V v) {
            this.data = v;
            this.outgoing = new ArrayList<>();
            this.incoming = new ArrayList<>();
            this.label = null;
            this.visited = false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            VertexNode<?> that = (VertexNode<?>) o;
            return data.equals(that.data) &&
                    owner.equals(that.owner);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, owner);
        }

        @Override
        public V get() {
            return this.data;
        }

        @Override
        public void put(V v) {
            this.data = v;
        }
    }

    //Class for an edge of type E
    private final class EdgeNode<E> implements Edge<E> {
        E data;
        Graph<V, E> owner;
        VertexNode<V> from;
        VertexNode<V> to;
        Object label;

        // Constructor for a new edge
        EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
            this.from = f;
            this.to = t;
            this.data = e;
            this.label = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EdgeNode<?> edgeNode = (EdgeNode<?>) o;
            return owner.equals(edgeNode.owner) &&
                    from.equals(edgeNode.from) &&
                    to.equals(edgeNode.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(owner, from, to);
        }

        @Override
        public E get() {
            return this.data;
        }

        @Override
        public void put(E e) {
            this.data = e;
        }
    }



    private List<Vertex<V>> vertices;
    private List<Edge<E>> edges;

    /** Constructor for instantiating a graph. */
    public SparseGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    // Checks vertex belongs to this graph
    private void checkOwner(VertexNode<V> toTest) {
        if (toTest.owner != this) {
            throw new PositionException();
        }
    }

    // Checks edge belongs to this graph
    private void checkOwner(EdgeNode<E> toTest) {
        if (toTest.owner != this) {
            throw new PositionException();
        }
    }

    // Converts the vertex back to a VertexNode to use internally
    private VertexNode<V> convert(Vertex<V> v) throws PositionException {
        try {
            VertexNode<V> gv = (VertexNode<V>) v;
            this.checkOwner(gv);
            return gv;
        } catch (ClassCastException ex) {
            throw new PositionException();
        }
    }

    // Converts and edge back to a EdgeNode to use internally
    private EdgeNode<E> convert(Edge<E> e) throws PositionException {
        try {
            EdgeNode<E> ge = (EdgeNode<E>) e;
            this.checkOwner(ge);
            return ge;
        } catch (ClassCastException ex) {
            throw new PositionException();
        }
    }

    @Override
    public Vertex<V> insert(V v) {
        if (v == null) {
            throw new IllegalArgumentException();
        }

        VertexNode<V> vert = new VertexNode<>(v);
        vert.owner = this;
        this.vertices.add(vert);
        return vert;
    }

    @Override
    public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
            throws PositionException, InsertionException {
        if (e == null) {
            throw new IllegalArgumentException();
        }

        VertexNode<V> start = convert(from);
        VertexNode<V> stop = convert(to);

        if (start.equals(stop)) {
            throw new InsertionException();
        }

        EdgeNode<E> edg = new EdgeNode<>(start, stop, e);
        edg.owner = this;

        for (Edge<E> edg1 : start.outgoing) {
            if (convert(edg1).equals(edg)) {
                throw new InsertionException();
            }
        }

        this.edges.add(edg);
        start.outgoing.add(edg);
        stop.incoming.add(edg);
        return edg;
    }

    @Override
    public V remove(Vertex<V> v) throws PositionException,
            RemovalException {

        VertexNode<V> vert = convert(v);
        if (vert.outgoing.size() != 0
                || vert.incoming.size() != 0) {
            throw new RemovalException();
        }

        V val = vert.data;
        this.vertices.remove(vert);
        vert.owner = null;
        return val;
    }

    @Override
    public E remove(Edge<E> e) throws PositionException {

        EdgeNode<E> edg = convert(e);

        VertexNode<V> start = convert(edg.from);
        VertexNode<V> stop = convert(edg.to);

        start.outgoing.remove(edg);
        stop.incoming.remove(edg);

        E val = edg.data;
        this.edges.remove(edg);
        edg.owner = null;
        return val;
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        List<Vertex<V>> copy = new ArrayList<>(vertices.size());
        copy.addAll(vertices);

        return copy;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        List<Edge<E>> copy = new ArrayList<>(edges.size());
        copy.addAll(edges);

        return copy;
    }

    @Override
    public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
        VertexNode<V> vert = convert(v);

        List<Edge<E>> copy = new ArrayList<>(vert.outgoing.size());
        copy.addAll(vert.outgoing);

        return copy;
    }

    @Override
    public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
        VertexNode<V> vert = convert(v);

        List<Edge<E>> copy = new ArrayList<>(vert.incoming.size());
        copy.addAll(vert.incoming);

        return copy;
    }

    @Override
    public Vertex<V> from(Edge<E> e) throws PositionException {
        EdgeNode<E> edg = convert(e);
        return edg.from;
    }

    @Override
    public Vertex<V> to(Edge<E> e) throws PositionException {
        EdgeNode<E> edg = convert(e);
        return edg.to;
    }

    @Override
    public void label(Vertex<V> v, Object l) throws PositionException {
        VertexNode<V> vert = convert(v);
        vert.label = l;
    }

    @Override
    public void label(Edge<E> e, Object l) throws PositionException {
        EdgeNode<E> edg = convert(e);
        edg.label = l;
    }

    @Override
    public Object label(Vertex<V> v) throws PositionException {
        VertexNode<V> vert = convert(v);
        return vert.label;
    }

    @Override
    public Object label(Edge<E> e) throws PositionException {
        EdgeNode<E> edg = convert(e);
        return edg.label;
    }

    /**
     * Get the amount of distance between the node and the start node.
     * @param v the vertex
     * @param d the distance you want to set the vertex to
     * @throws PositionException if vertex isn't valid
     */
    public void setDistance(Vertex<V> v, double d) throws PositionException {
        VertexNode<V> vert = convert(v);
        vert.distance = d;
    }

    /**
     * Get the amount of distance between the node and the start node.
     * @param v the vertex
     * @return true if visited false if not
     * @throws PositionException if vertex isn't valid
     */
    public double getDistance(Vertex<V> v) throws PositionException {
        VertexNode<V> vert = convert(v);
        return vert.distance;
    }

    /**
     * Mark a vertex as having been visited.
     * @param v the vertex
     */
    public void visit(Vertex<V> v) {
        VertexNode<V> vert = convert(v);
        vert.visited = true;
    }

    /**
     * Check if a vertex has already been visited.
     * @param v the vertex
     * @return true if visited false if not
     * @throws PositionException if vertex isn't valid
     */
    public boolean hasVisited(Vertex<V> v) throws PositionException {
        VertexNode<V> vert = convert(v);
        return vert.visited;
    }

    @Override
    public void clearLabels() {
        for (Vertex<V> v : this.vertices) {
            convert(v).label = null;
        }

        for (Edge<E> e : this.edges) {
            convert(e).label = null;
        }
    }

    /**
     * Initialize all distances to very large number.
     * @param d initial distance of vertex initial
     */
    public void initializeDists(double d) {
        // Set starting distances
        for (Vertex<V> vert : vertices) {
            this.setDistance(convert(vert), d);
        }
    }

    private String vertexString(Vertex<V> v) {
        return "\"" + v.get() + "\"";
    }

    private String verticesToString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex<V> v : this.vertices) {
            sb.append("  ").append(vertexString(v)).append("\n");
        }
        return sb.toString();
    }

    private String edgeString(Edge<E> e) {
        return String.format("%s -> %s [label=\"%s\"]",
                this.vertexString(this.from(e)),
                this.vertexString(this.to(e)),
                e.get());
    }

    private String edgesToString() {
        StringBuilder edgs = new StringBuilder();
        for (Edge<E> e : this.edges) {
            edgs.append("    ").append(this.edgeString(e)).append(";\n");
        }
        return edgs.toString();
    }

    @Override
    public String toString() {
        return String.format("digraph {\n%s%s}",
                this.verticesToString(),
                this.edgesToString());
    }
}
