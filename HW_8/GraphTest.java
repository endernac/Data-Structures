package hw8.tests;

import hw8.Graph;
import hw8.Vertex;
import hw8.Edge;

import java.util.Iterator;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class GraphTest {

    private final class TestVertex<V> implements Vertex<V> {
        @Override
        public V get() {
            return null;
        }

        @Override
        public void put(V v) {
            return;
        }
    }

    private final class TestEdge<V> implements Edge<V> {
        @Override
        public V get() {
            return null;
        }

        @Override
        public void put(V v) {
            return;
        }
    }

    protected Graph<String, String> graph;

    protected abstract Graph<String, String> createGraph();

    @Before
    public void setupGraph() {
        this.graph = createGraph();
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNullVertex() {
        graph.insert(null);
    }

    @Test
    public void insertVertex() {
        Vertex v = graph.insert("V1");
        graph.label(v, "l1");
        assertEquals(graph.label(v), "l1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNullEdge() {
        Vertex v1 = graph.insert("V1");
        Vertex v2 = graph.insert("V2");
        Edge e1 = graph.insert(v1, v2, null);
    }

    @Test(expected = PositionException.class)
    public void insertEdgeBadVertex() {
        Vertex v1 = graph.insert("V1");
        Vertex v2 = new TestVertex();
        Edge e1 = graph.insert(v1, v2, "e1");
    }

    @Test(expected = InsertionException.class)
    public void insertSelfLoop1() {
        Vertex v1 = graph.insert("V1");
        Edge e1 = graph.insert(v1, v1, "e1");
    }

    @Test(expected = InsertionException.class)
    public void insertSelfLoop2() {
        Vertex v1 = graph.insert("V1");
        Vertex v2 = graph.insert("V1");
        Edge e1 = graph.insert(v1, v2, "e1");
    }

    @Test(expected = InsertionException.class)
    public void insertDoubleEdge() {
        Vertex v1 = graph.insert("V1");
        Vertex v2 = graph.insert("V2");
        Edge e1 = graph.insert(v1, v2, "e1");
        Edge e2 = graph.insert(v1, v2, "e1");
    }

    @Test
    public void insertEdge() {
        Vertex v1 = graph.insert("V1");
        Vertex v2 = graph.insert("V2");
        Edge e1 = graph.insert(v1, v2, "e1");
        graph.label(e1, "e1");
        assertEquals(graph.label(e1), "e1");
    }

    @Test(expected = PositionException.class)
    public void removeInvalidVertex() {
        Vertex v1 = new TestVertex();
        graph.remove(v1);
    }

    @Test(expected = RemovalException.class)
    public void removeConnectedVertex1() {
        Vertex v1 = graph.insert("V1");
        Vertex v2 = graph.insert("V2");
        Edge e1 = graph.insert(v1, v2, "e1");
        graph.remove(v1);
    }

    @Test(expected = RemovalException.class)
    public void removeConnectedVertex2() {
        Vertex v1 = graph.insert("V1");
        Vertex v2 = graph.insert("V2");
        Edge e1 = graph.insert(v1, v2, "e1");
        graph.remove(v2);
    }

    @Test
    public void removeVertex1() {
        Vertex v1 = graph.insert("V1");
        String v = graph.remove(v1);
        assertEquals(v, "V1");
    }

    @Test(expected = PositionException.class)
    public void removeVertex2() {
        Vertex v1 = graph.insert("V1");
        graph.label(v1, "v1");
        graph.remove(v1);
        graph.label(v1);
    }

    @Test(expected = PositionException.class)
    public void removeInvalidEdge() {
        Edge e1 = new TestEdge();
        graph.remove(e1);
    }

    @Test
    public void removeEdge1() {
        Vertex v1 = graph.insert("V1");
        Vertex v2 = graph.insert("V2");
        Edge e1 = graph.insert(v1, v2, "e1");
        String e = graph.remove(e1);
        assertEquals(e, "e1");
    }

    @Test(expected = PositionException.class)
    public void removeEdge2() {
        Vertex v1 = graph.insert("V1");
        Vertex v2 = graph.insert("V2");
        Edge e1 = graph.insert(v1, v2, "e1");
        graph.label(e1, "e1");
        graph.remove(e1);
        graph.label(e1);
    }

    @Test
    public void verticesTest() {
        String[] vertNames = {"v1", "v2", "v3"};

        for (String s : vertNames) {
            Vertex v = graph.insert(s);
            graph.label(v, s);
        }

        int i = 0;
        for (Vertex<String> v : graph.vertices()) {
            assertEquals(graph.label(v), vertNames[i]);
            i++;
        }
    }

    @Test
    public void verticesRemoveTest() {
        String[] vertNames = {"v1", "v2", "v3"};

        for (String s : vertNames) {
            Vertex v = graph.insert(s);
            graph.label(v, s);
        }

        Iterator it = graph.vertices().iterator();
        while (it.hasNext()) {
            Vertex v = (Vertex) it.next();
            if (graph.label(v).equals("v2")) {
                it.remove();
                break;
            }
        }

        int i = 0;
        for (Vertex<String> v : graph.vertices()) {
            assertEquals(graph.label(v), vertNames[i]);
            i++;
        }
    }

    @Test
    public void edgesTest() {
        String[] edgNames = {"e1", "e2", "e3"};

        Vertex v1 = graph.insert("v1");
        Vertex v2 = graph.insert("v2");
        Vertex v3 = graph.insert("v3");


        Edge e1 = graph.insert(v1, v2, edgNames[0]);
        graph.label(e1, edgNames[0]);
        Edge e2 = graph.insert(v2, v3, edgNames[1]);
        graph.label(e2, edgNames[1]);
        Edge e3 = graph.insert(v3, v1, edgNames[2]);
        graph.label(e3, edgNames[2]);

        int i = 0;
        for (Edge<String> e : graph.edges()) {
            assertEquals(graph.label(e), edgNames[i]);
            i++;
        }
    }

    @Test
    public void edgesRemoveTest() {
        String[] edgNames = {"e1", "e2", "e3"};

        Vertex v1 = graph.insert("v1");
        Vertex v2 = graph.insert("v2");
        Vertex v3 = graph.insert("v3");


        Edge e1 = graph.insert(v1, v2, edgNames[0]);
        graph.label(e1, edgNames[0]);
        Edge e2 = graph.insert(v2, v3, edgNames[1]);
        graph.label(e2, edgNames[1]);
        Edge e3 = graph.insert(v3, v1, edgNames[2]);
        graph.label(e3, edgNames[2]);

        Iterator it = graph.edges().iterator();
        while (it.hasNext()) {
            Edge e = (Edge) it.next();
            if (graph.label(e).equals("e2")) {
                it.remove();
                break;
            }
        }

        int i = 0;
        for (Edge<String> e : graph.edges()) {
            assertEquals(graph.label(e), edgNames[i]);
            i++;
        }

    }

    @Test(expected = PositionException.class)
    public void incomingInvalidVertex() {
        Vertex v1 = new TestVertex();
        graph.incoming(v1);
    }

    @Test
    public void incomingTest() {
        String[] edgNames = {"e1", "e2", "e3"};

        Vertex v1 = graph.insert("v1");
        Vertex v2 = graph.insert("v2");
        Vertex v3 = graph.insert("v3");
        Vertex v4 = graph.insert("v4");

        int i = 0;
        for (Vertex v : graph.vertices()) {
            if (!v.equals(v1)) {
                Edge e = graph.insert(v, v1, edgNames[i]);
                graph.label(e, edgNames[i]);
                i++;
            }
        }

        int j = 0;
        for (Object e : graph.incoming(v1)) {
            assertEquals(graph.label((Edge) e), edgNames[j]);
            j++;
        }
    }

    @Test
    public void incomingRemoveTest() {
        String[] edgNames = {"e1", "e2", "e3"};

        Vertex v1 = graph.insert("v1");
        Vertex v2 = graph.insert("v2");
        Vertex v3 = graph.insert("v3");
        Vertex v4 = graph.insert("v4");

        int i = 0;
        for (Vertex v : graph.vertices()) {
            if (!v.equals(v1)) {
                Edge e = graph.insert(v, v1, edgNames[i]);
                graph.label(e, edgNames[i]);
                i++;
            }
        }

        Iterator it = graph.incoming(v1).iterator();
        while (it.hasNext()) {
            Edge e = (Edge) it.next();
            if (graph.label(e).equals("e2")) {
                it.remove();
                break;
            }
        }

        int j = 0;
        for (Object e : graph.incoming(v1)) {
            assertEquals(graph.label((Edge) e), edgNames[j]);
            j++;
        }

    }

    @Test(expected = PositionException.class)
    public void outgoingInvalidVertex() {
        Vertex v1 = new TestVertex();
        graph.outgoing(v1);
    }

    @Test
    public void outgoingTest() {
        String[] edgNames = {"e1", "e2", "e3"};

        Vertex v1 = graph.insert("v1");
        Vertex v2 = graph.insert("v2");
        Vertex v3 = graph.insert("v3");
        Vertex v4 = graph.insert("v4");

        int i = 0;
        for (Vertex v : graph.vertices()) {
            if (!v.equals(v1)) {
                Edge e = graph.insert(v1, v, edgNames[i]);
                graph.label(e, edgNames[i]);
                i++;
            }
        }

        int j = 0;
        for (Object e : graph.outgoing(v1)) {
            assertEquals(graph.label((Edge) e), edgNames[j]);
            j++;
        }
    }

    @Test
    public void outgoingRemoveTest() {
        String[] edgNames = {"e1", "e2", "e3"};

        Vertex v1 = graph.insert("v1");
        Vertex v2 = graph.insert("v2");
        Vertex v3 = graph.insert("v3");
        Vertex v4 = graph.insert("v4");

        int i = 0;
        for (Vertex v : graph.vertices()) {
            if (!v.equals(v1)) {
                Edge e = graph.insert(v1, v, edgNames[i]);
                graph.label(e, edgNames[i]);
                i++;
            }
        }

        Iterator it = graph.outgoing(v1).iterator();
        while (it.hasNext()) {
            Edge e = (Edge) it.next();
            if (graph.label(e).equals("e2")) {
                it.remove();
                break;
            }
        }

        int j = 0;
        for (Object e : graph.outgoing(v1)) {
            assertEquals(graph.label((Edge) e), edgNames[j]);
            j++;
        }

    }

    @Test(expected = PositionException.class)
    public void fromInvalidEdge() {
        Edge e = new TestEdge();
        graph.from(e);
    }

    @Test
    public void fromEdge() {
        Vertex v1 = graph.insert("v1");
        Vertex v2 = graph.insert("v2");
        Edge e1 = graph.insert(v1, v2, "e1");
        graph.label(v1, "v1");
        assertEquals(graph.label(graph.from(e1)), "v1");
    }

    @Test(expected = PositionException.class)
    public void toInvalidEdge() {
        Edge e = new TestEdge();
        graph.to(e);
    }

    @Test
    public void toEdge() {
        Vertex v1 = graph.insert("v1");
        Vertex v2 = graph.insert("v2");
        Edge e1 = graph.insert(v1, v2, "e1");
        graph.label(v2, "v2");
        assertEquals(graph.label(graph.to(e1)), "v2");
    }



}
