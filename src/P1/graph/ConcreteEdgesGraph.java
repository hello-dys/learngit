/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO


    private void checkRep(){
        for(Edge<L> e : edges){
            assert vertices.contains(e.getSource());
            assert vertices.contains(e.getTarget());
        }
    }

    @Override
    public boolean add(L vertex) {
        final boolean v = vertices.add(vertex);
        checkRep();
        return v;
    }

    @Override
    public int set(L source, L target, int weight) {
        int pre = 0;
        for(Edge<L> e : edges){
            if(e.getSource().equals(source) && e.getTarget().equals(target)){
                pre = e.getWeight();
                edges.remove(e);
                break;
            }
        }

        if(weight > 0){
            edges.add(new Edge<>(source, target, weight));
            vertices.add(source);
            vertices.add(target);
        }

        checkRep();
        return pre;
    }

    @Override
    public boolean remove(L vertex) {

        edges.removeIf(e -> e.getSource().equals(vertex) ||
                e.getTarget().equals(vertex));

        final boolean containsVertex = vertices.remove(vertex);
        checkRep();
        return containsVertex;
    }

    @Override public Set<L> vertices() {
        return new HashSet<>(vertices);
    }

    @Override public Map<L, Integer> sources(L target) {
        final Map<L, Integer> sources = new HashMap<>();

        for(Edge<L> e : edges){
            if(e.getTarget().equals(target)){
                sources.put(e.getSource(), e.getWeight());
            }
        }

        checkRep();
        return sources;
    }

    @Override public Map<L, Integer> targets(L source) {
        final Map<L, Integer> targets = new HashMap<>();

        for(Edge<L> e : edges){
            if(e.getSource().equals(source)){
                targets.put(e.getTarget(), e.getWeight());
            }
        }

        checkRep();
        return targets;
    }

    // TODO toString()

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph {\n");

        sb.append("\tVertices: ")
                .append(String.join(", ", vertices.toString()))
                .append('\n');

        sb.append("\tEdges:\n");
        for (Edge<L> e : edges) {
            sb.append("\t\t").append(e.getSource())
                    .append("->").append(e.getTarget())
                    .append(':').append(e.getWeight())
                    .append('\n');
        }
        sb.append("}");

        return sb.toString();
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {

    private final  L source;
    private final L target;
    private final int weight;

    // Abstraction function:
    //   represents directed edge source --> target
    // Representation invariant:
    //   weight > 0
    // Safety from rep exposure:
    //   all fields are private and immutable

    /**
     * construct a weighted edge source --> target
     * @param source label representing source of this edge
     * @param target label representing target of this edge
     * @param weight weight of this edge; must be a positive value (>0)
     */
    public Edge(L source, L target, int weight){
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    private void checkRep(){
        assert weight > 0;
    }

    /**
     *
     * @return a label representing the target of this edge
     */
    public L getTarget(){
        return target;
    }

    /**
     *
     * @return a label representing the source of this edge
     */
    public L getSource(){
        return source;
    }
    /**
     *
     * @return a positive integer representing weight of this edge
     */
    public int getWeight(){
        return weight;
    }

    /**
     * Returns a string representation of this edge in the form: "(source, target, weight)". Each of the elements
     * of this tuple represent the string form of the respective labeled component of this edge as given by toString().
     * @return a string representation of this edge
     */
    @Override public String toString() {
        return String.format("Edge(%s->%s, weight = %d)", source, target, weight);
    }
}
