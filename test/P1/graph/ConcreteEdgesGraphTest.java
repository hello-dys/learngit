/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * <p>
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * <p>
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<>();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */

    // Testing strategy for ConcreteEdgesGraph.toString()
    //   Insert several vertices and edges, then check if the output is correct

    @Test
    public void test_graph_toString() {
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

        graph.remove(a);
        Assert.assertEquals("""
                Graph {
                \tVertices: [b]
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
    }

    /*
     * Testing Edge...
     */

    // Testing strategy for Edge
    //    1. Test constructor
    //        if success when source not equals to target
    //        if error when source equals to target
    //        if error when weight == 0
    //        if error when weight < 0
    //    2. test all get methods
    //    3. test toString method

    @Test
    public void test_edge_constructor() {
        String a = "a";
        String b = "b";
        String anotherA = "a";

        new Edge<>(a, b, 1);

        try {
            new Edge<>(a, anotherA, 1);
            throw new AssertionError();
        } catch (AssertionError ignored) {
        }

        try {
            new Edge<>(a, b, 0);
            throw new AssertionError();
        } catch (AssertionError ignored) {
        }

        try {
            new Edge<>(a, b, -1);
            throw new AssertionError();
        } catch (AssertionError ignored) {
        }
    }

    @Test
    public void test_edge_get() {
        String a = "a";
        String b = "b";
        int w = 1;
        Edge<String> edge = new Edge<>(a, b, w);

        Assert.assertEquals(a, edge.getSource());
        Assert.assertEquals(b, edge.getTarget());
        Assert.assertEquals(w, edge.getWeight());
    }

    @Test
    public void test_edge_toString() {
        String a = "a";
        String b = "b";
        int w = 2;
        Edge<String> edge = new Edge<>(a, b, w);

        Assert.assertEquals(String.format("Edge(%s->%s, weight = %d)", a, b, w), edge.toString());
    }
}
