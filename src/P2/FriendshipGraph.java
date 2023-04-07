package P2;

import P1.graph.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FriendshipGraph<L> {

    private final Graph<Person> graph;

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
