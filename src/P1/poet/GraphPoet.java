/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 *
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 *
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 *
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 *
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 *
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications,
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //AF(graph) = Directed Graph D = (V, E)
    //       V = graph.vertices()
    //       E = src->v weights w for src: w in graph.sources(v)
    //             and v->tar weights w for tar: w in graph.targets(v)
    //             for v in V
    // Representation invariant:
    // graph中没有孤立的点
    // Safety from rep exposure:
    //  每条边的权值都大于0

    /**
     * Create a new poet with the graph from corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        FileReader f = null;
        BufferedReader f1 = null;
        f = new FileReader(corpus);
        f1 = new BufferedReader(f);
        String sentences = null;
        while((sentences =  f1.readLine()) != null) {
            String[] words = sentences.split(" ");
            for(int i = 0; i < words.length - 1; i++) {
                int w = getWeight(words[i], words[i + 1]);
                graph.set(words[i].trim().toLowerCase(), words[i + 1].toLowerCase(), w + 1);
            }
        }
        checkRep();
        f1.close();
        f.close();
    }

    private int getWeight(String src, String tar){
        Map<String, Integer> map = graph.targets(src);
        if (!map.containsKey(tar)){
            return 0;
        }
        return map.get(tar);
    }

    private void checkRep(){
        for (String str : graph.vertices()){
            for (int value : graph.sources(str).values()){
                assert value > 0;
            }
            for (int value : graph.targets(str).values()){
                assert value > 0;
            }
        }
    }
    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        StringBuilder poem = new StringBuilder();
        String[] words = input.split(" ");
        for(int i = 0; i < words.length - 1; i++) {
            String src = words[i].toLowerCase();
            String tar = words[i + 1].toLowerCase();
            String bridge = getBridge(src, tar);
            if (bridge != null){
                poem.append(words[i]).append(" ").append(bridge).append(" ");
            }
            else {
                poem.append(words[i]).append(" ");
            }

        }
        poem.append(words[words.length - 1]);
        return poem.toString();
    }

    private String getBridge(String src, String tar){
        Map<String, Integer> Bridge_src = graph.targets(src);
        Map<String, Integer> Bridge_tar = graph.sources(tar);
        Set<String> bridgeSet = new HashSet<>(Bridge_src.keySet());
        bridgeSet.retainAll(Bridge_tar.keySet());
        String bestBridge = null;
        int max = -1;
        for (String bridge : bridgeSet) {
            int w = Bridge_src.get(bridge) + Bridge_tar.get(bridge); 
            if (bestBridge == null) { 
                bestBridge = bridge;
                max = w;
            } 
            else { 
                if (w > max) { 
                    bestBridge = bridge;
                    max = w;
                }
            }
        }
        return bestBridge;
    }

    // TODO toString()
    @Override
    public String toString() {
        String result = graph.toString();
        return "GraphPoet" + result.substring(5);
    }
}
