/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices) = Directed Graph D = (V, E)
    //       V = all vertex.getSources() and vertex.getTargets() union together for vertex in vertices
    //       E = from src to vertex.getName for src in vertex.getSources()
    //             unions from vertex.getName to tar for tar in vertex.getTargets()
    //             for vertex in vertices
    // Representation invariant:
    // 所有targets和sources都在graph中
    // Safety from rep exposure:
    //  不能有重复边

    private void checkRep(){
        for (int i = 0; i < vertices.size(); i++){
            for (int j = i + 1; j < vertices.size(); j++){
                assert !vertices.get(i).equals(vertices.get(j));
            }
        }
    }

    @Override public boolean add(L vertex) {
        checkRep();
        assert vertex != null;
        for (Vertex<L> v : vertices){
            if (vertex.equals(v.getName())){
                return false;
            }
        }
        return vertices.add(new Vertex<>(vertex));
    }
    
    @Override public int set(L source, L target, int weight) {
        if (!vertices.stream().map(Vertex::getName).collect(Collectors.toSet()).containsAll(Arrays.asList(source, target))) {
            vertices.addAll(Arrays.asList(new Vertex<>(source), new Vertex<>(target)));
        }
        vertices.stream().filter(v -> source.equals(v.getName()))
                .forEach(v -> v.setTarget(target, weight));
        vertices.stream().filter(v -> target.equals(v.getName()))
                .forEach(v -> v.setSource(source, weight));
        return 0;
    }


    @Override public boolean remove(L vertex) {
        assert vertex != null;
        vertices.removeIf(v -> vertex.equals(v.getName()));
        for (Vertex<L> v : vertices){
            v.removeSource(vertex);
            v.removeTarget(vertex);
        }
        return true;
    }

    @Override public Set<L> vertices() {
        Set<L> nameSet = new HashSet<>();
        for (Vertex<L> v : vertices){
            nameSet.add(v.getName());
        }
        return nameSet;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> set = null;
        for (Vertex<L> v : vertices){
            if (target.equals(v.getName())){
                set = v.getSources();
            }
        }
        return set;
    }

    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> set = null;
        for (Vertex<L> v : vertices){
            if (source.equals(v.getName())){
                set = v.getTargets();
            }
        }
        return set;
    }
    
    // TODO toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph {\n");
        sb.append("\tVertices: ")
                .append(getNameSet())
                .append('\n');
        sb.append("\tEdges:\n");
        for (Vertex<L> vertex : vertices) {
            L name = vertex.getName();
            Map<L, Integer> targets = vertex.getTargets();
            targets.forEach((target, weight) ->
                    sb.append("\t\t").append(name)
                            .append("->").append(target)
                            .append(':').append(weight)
                            .append('\n')
            );
        }
        sb.append("}");
        return sb.toString();
    }


    private Set<L> getNameSet() {
        Set<L> nameSet = new HashSet<>();

        for (Vertex<L> v : vertices)
            nameSet.add(v.getName());

        return nameSet;
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // TODO fields

    private final L name;
    private final Map<L, Integer> sources = new HashMap<>();
    private final Map<L, Integer> targets = new HashMap<>();

    public Vertex(L vertex){
        name = vertex;
        checkRep();
    }

    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO

    private void checkRep() {
        assert name != null;
    }

    public L getName(){
        return name;
    }

    public Map<L, Integer> getSources(){
        return new HashMap<>(sources);
    }

    public Map<L, Integer> getTargets(){
        return new HashMap<>(targets);
    }

    public void removeSource(L source){
        if (source != null){
            sources.remove(source);
        }
    }
    public void removeTarget(L target){
        if (target != null){
            targets.remove(target);
        }
    }
    public int setSource(L source, int weight){
        assert weight >= 0;
        if (weight > 0){
            sources.put(source, weight);
            return weight;
        }
        else {
            removeSource(source);
        }
        return 0;
    }
    public int setTarget(L target, int weight){
        assert weight >= 0;
        if (weight > 0){
            targets.put(target, weight);
            return weight;
        }
        else {
            removeSource(target);
        }
        return 0;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Vertex {\n");
        sources.forEach((source, weight) ->
                sb.append('\t').append(source)
                        .append("->").append(name)
                        .append(':').append(weight)
                        .append('\n')
        );
        targets.forEach((target, weight) ->
                sb.append('\t').append(name)
                        .append("->").append(target)
                        .append(':').append(weight)
                        .append('\n')
        );
        sb.append("}");
        return sb.toString();
    }
}
