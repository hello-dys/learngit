package P3;

public class GraphTest {

    public static void main(String[] args) {

        Graph.Person aa = new Graph.Person("aaa");
        Graph.Person bb = new Graph.Person("bbb");
        Graph.Person cc = new Graph.Person("ccc");
        Graph.Person dd = new Graph.Person("ddd");
        Graph.Person ee = new Graph.Person("eee");
        Graph.Person ff = new Graph.Person("fff");
        Graph.Person gg = new Graph.Person("ggg");

        //存放顶点的数组
        String vertex[] = Graph.s.toArray(new String[0]);

        //将数组的长度传入无向图对象
        Graph<String> graph = new Graph<>(vertex.length);

        //将顶点添加到图的顶点集合中
        graph.addVertex(aa.name);
        graph.addVertex(bb.name);
        graph.addVertex(cc.name);
        graph.addVertex(dd.name);
        graph.addVertex(ee.name);
        graph.addVertex(ff.name);
        graph.addVertex(gg.name);

        //添加边
        graph.addEdge(aa, bb);
        graph.addEdge(aa, cc);
        graph.addEdge(aa, ff);
        graph.addEdge(bb, cc);
        graph.addEdge(bb, dd);
        graph.addEdge(bb, ee);
        graph.addEdge(ff, gg);

        //两点之间的距离
        System.out.println(graph.getDistance(aa, gg));
        System.out.println(graph.getDistance(bb, gg));
    }

}
