package P3;

import java.util.ArrayList;
public class FriendshipGraph<T> {
    //顶点的数量
    private int vCount ;
    //边的数量
    private int edgeCount;
    //存储顶点的集合，我可以使用顶点的下标代替顶点
    private ArrayList<T> vertex;
    //使用二维数组表示邻接矩阵
    private int[][] matrix;

    //初始化图,n指的是顶点的数量
    public FriendshipGraph(){
        //顶点集合的大小是顶点的个数
        vertex = new ArrayList<>();
        //顶点的个数是二维数组的长度
        matrix = new int[100][100];
        //顶点数量、边数量初始化为0
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
        return graph[vertex.indexOf(p1.getName())][vertex.indexOf(p2.getName())];
    }
}

