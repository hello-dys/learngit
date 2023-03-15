package test;

import P3.FriendshipGraph;
import P3.Person;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FriendshipGraphTest {
    @Test
    public void test(){
        FriendshipGraph<String> graph = new FriendshipGraph<>();
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addEdge(a, b);
        graph.addEdge(b, c);
        graph.addEdge(c, d);
        assertEquals("expected distance", 1, graph.getDistance(a, b));
        assertEquals("expected distance", 1, graph.getDistance(b, c));
        assertEquals("expected distance", 1, graph.getDistance(c, d));
        assertEquals("expected distance", 2, graph.getDistance(a, c));
        assertEquals("expected distance", 2, graph.getDistance(b, d));
        assertEquals("expected distance", 3, graph.getDistance(a, d));
    }
}