package P2;

import P1.graph.Graph;

import java.util.*;

public class FriendshipGraph<L> {

    private final Graph<Person> graph;

    // Abstraction function:
    //   AF(graph) = Directed Graph D = (V, E) refers to a friendship graph
    //       V = graph.vertices()
    //       E = from src to v for src in graph.sources(v).keySet()
    //             unions from v to tar for tar in graph.targets(v).keySet()
    //             for v in V
    // Representation invariant:
    //   graph != null
    // Safety from rep exposure:
    //  每条边都在点集中

    private void checkRep() {
        Set<Person> peopleSet = graph.vertices();
        for (Person p : peopleSet) {
            assert !graph.sources(p).containsKey(p);
            assert !graph.targets(p).containsKey(p);
        }
    }

    public FriendshipGraph() {
        graph = Graph.empty();
    }

    public void addVertex(Person person){
        graph.add(person);
    }

    public void addEdge(Person p1, Person p2){
        graph.set(p1, p2, 1);
    }

    public int getDistance(Person source, Person destination) {
        Map<Person, Integer> distances = new HashMap<>();
        Queue<Person> queue = new LinkedList<>();
        queue.add(source);
        distances.put(source, 0);

        while (!queue.isEmpty()) {
            Person current = queue.remove();
            for (Person neighbor : graph.targets(current).keySet()) {
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, distances.get(current) + 1);
                    if (neighbor.equals(destination)) {
                        return distances.get(neighbor);
                    }
                    queue.add(neighbor);
                }
            }
        }
        checkRep();
        return -1; // No path found between source and destination
    }

    public static void main(String[] args) {

        FriendshipGraph<String> graph = new FriendshipGraph<>();

        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");

        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);

        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);

        System.out.println(graph.getDistance(rachel, ross));
        System.out.println(graph.getDistance(rachel, ben));
        System.out.println(graph.getDistance(rachel, rachel));
        System.out.println(graph.getDistance(rachel, kramer));
    }
}
