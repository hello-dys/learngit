/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO

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
        Set<L> nameSet = new HashSet<>();
        for (Vertex<L> v : vertices){
            nameSet.add(v.getName());
        }
        if (!nameSet.contains(source)){
            vertices.add(new Vertex<>(source));
        }
        if (!nameSet.contains(target)){
            vertices.add(new Vertex<>(target));
        }
        for (Vertex<L> v : vertices){
            if (source.equals(v.getName())){
                v.setTarget(target, weight);
            } else if (target.equals(v.getName())) {
                v.setSource(source, weight);
            }
        }
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
        StringBuilder sb = new StringBuilder();
        sb.append("Graph {\n");

        sb.append("\tVertices: ")
                .append(String.join(", ", getNameSet().toString()))
                .append('\n');

        sb.append("\tEdges:\n");
        for (Vertex<L> v : vertices) {
            L name = v.getName();
            Map<L, Integer> targetsMap = v.getTargets();
            for (L tarName : targetsMap.keySet()) {
                sb.append("\t\t").append(name)
                        .append("->").append(tarName)
                        .append(':').append(targetsMap.get(tarName))
                        .append('\n');
            }
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
        StringBuilder sb = new StringBuilder();
        sb.append("Vertex {\n");
        for (L source : sources.keySet()) {
            sb.append('\t').append(source).append("->").append(name).append(':').append(sources.get(source)).append('\n');
        }
        for (L target : targets.keySet()) {
            sb.append('\t').append(name).append("->").append(target).append(':').append(targets.get(target)).append('\n');
        }
        sb.append("}");
        return sb.toString();
    }
}
