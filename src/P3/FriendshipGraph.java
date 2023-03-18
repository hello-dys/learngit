package P3;

import java.util.ArrayList;
public class FriendshipGraph<T> {
    private int vCount ;
    private int edgeCount;
    private ArrayList<T> vertex;
    private int[][] matrix;

    public FriendshipGraph(){
        vertex = new ArrayList<>();
        matrix = new int[100][100];
        vCount = 0;
        edgeCount = 0;
    }

    //插入节点
    public void addVertex(Person p){
        vertex.add((T) p.getName());
        vCount++;
    }

    //添加边
    public void addEdge(Person p1, Person p2){
        int v1 = vertex.indexOf(p1.getName());
        int v2 = vertex.indexOf(p2.getName());
        if (v1 != v2){
            matrix[v1][v2] = 1;
            edgeCount++;
        }
    }

    //获取图中顶点的个数
    public int getVerCount(){
        return vCount;
    }

    //计算两点之间的距离
    public int getDistance(Person p1, Person p2) {
        int[][] graph = matrix;
        for (int i = 0; i < getVerCount(); i++){
            for (int j = 0; j < getVerCount(); j++){
                if (graph[i][j] == 0)
                    graph[i][j] = 9999;
            }
        }
        for (int i = 0; i < getVerCount(); i++) {
            for (int j = 0; j < getVerCount(); j++) {
                for (int k = 0; k < getVerCount(); k++) {
                    if (graph[j][i] + graph[i][k] < graph[j][k]) {
                        graph[j][k] = graph[j][i] + graph[i][k];
                    }
                }
            }
        }
        if (graph[vertex.indexOf(p1.getName())][vertex.indexOf(p2.getName())] == 9999){
            return -1;
        }
        if (p1.isSameName(p2.getName())){
            return 0;
        }
        return graph[vertex.indexOf(p1.getName())][vertex.indexOf(p2.getName())];
    }
    public static void main(String[] args) {
        FriendshipGraph graph = new FriendshipGraph();
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
        //should print 1
        System.out.println(graph.getDistance(rachel, ben));
        //should print 2
        System.out.println(graph.getDistance(rachel, rachel));
        //should print 0
        System.out.println(graph.getDistance(rachel, kramer));
        //should print -1
    }
}

