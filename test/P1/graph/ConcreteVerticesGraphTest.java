/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests for ConcreteVerticesGraph.
 * <p>
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * <p>
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */

    // Testing strategy for ConcreteVerticesGraph.toString()
    //   Insert several vertices and edges, then check if the output is correct

    @Test
    public void testGraphToString() {
        Graph<String> graph = emptyInstance();

        String a = "a";
        String b = "b";
        int w = 2;

        graph.add(a);
        graph.add(b);
        graph.set(a, b, w);
        graph.set(b, a, w);

        Assert.assertEquals("""
                Graph {
                \tVertices: [a, b]
                \tEdges:
                \t\ta->b:2
                \t\tb->a:2
                }""", graph.toString());

        graph.remove(b);
        Assert.assertEquals("""
                Graph {
                \tVertices: [a]
                \tEdges:
                }""", graph.toString());

    }

    @Test
    public void test_graph_get(){
        Graph<String> graph = emptyInstance();

        String a = "a";
        String b = "b";
        int w = 2;

        graph.add(a);
        graph.add(b);
        graph.set(a, b, w);
        graph.set(b, a, w);
        Assert.assertEquals("{b=2}", graph.sources(a).toString());
        Assert.assertEquals("{b=2}", graph.targets(a).toString());
        System.out.println(graph.sources(a));
    }
    /*
     * Testing Vertex...
     */

    // Testing strategy for Vertex
    //   test every public method of Vertex
    //   grouped by type of the method

    @Test
    public void testVertexConstructor() {
        String a = "a";
        new Vertex<>(a);
    }

    /**
     * Test all observers of Vertex
     */
    @Test
    public void testVertexGet() {
        String a = "a";
        String b = "b";
        int w = 1;

        Vertex<String> v = new Vertex<>(a);
        v.setTarget(b, w);

        Assert.assertEquals(a, v.getName()); // getName

        Assert.assertEquals(new HashMap<>(), v.getSources()); // getSources

        Map<String, Integer> set = new HashMap<>();
        set.put(b, w);
        Assert.assertEquals(set, v.getTargets()); // getTargets
    }

    /**
     * Test all mutators of Vertex
     */
    @Test
    public void testVertexUpdate() {
        String a = "a";
        String b = "b";
        int w1 = 1;
        int w2 = 2;

        Vertex<String> v = new Vertex<>(a);

        Assert.assertEquals(w2, v.setSource(b, w2));
        Assert.assertEquals(0, v.setSource(b, 0));
        Assert.assertEquals(w1, v.setTarget(b, w1));
        Assert.assertEquals(w2, v.setTarget(b, w2));

        Map<String, Integer> srcSet = new HashMap<>();
        Map<String, Integer> tarSet = new HashMap<>();
        tarSet.put(b, w2);
        Assert.assertEquals(srcSet, v.getSources());
        Assert.assertEquals(tarSet, v.getTargets());
    }

    @Test
    public void testVertexToString() {
        Vertex<String> v = new Vertex<>("a");
        v.setTarget("b", 1);
        v.setSource("c", 2);

        Assert.assertEquals("""
                Vertex {
                \tc->a:2
                \ta->b:1
                }""", v.toString());
    }
}
