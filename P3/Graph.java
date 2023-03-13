package P3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph<T> {
    //顶点的数量
    private int vCount;
    //边的数量
    private int edgeCount;
    //存储顶点的集合，我可以使用顶点的下标代替顶点
    private ArrayList<T> vertex;
    //使用二维数组表示邻接矩阵
    private int[][] matrix;
    //辅组数组，用来标记顶点是否被遍历,下标代表顶点
    boolean[] mark;


    //初始化图,n指的是顶点的数量
    Graph(int n){
        //顶点集合的大小是顶点的个数
        vertex = new ArrayList<>(n);
        //顶点的个数是二维数组的长度
        matrix = new int[n][n];
        //顶点数量、边数量初始化为0
        vCount = 0;
        edgeCount = 0;
        mark = new boolean[n];
    }

    //插入节点
    public void addVertex(T t){
        vertex.add(t);
        vCount++;
    }

    //添加边
    public void addEdge(int v1, int v2, int weight){
        matrix[v1][v2] = weight;
        matrix[v2][v1] = weight;
        edgeCount++;
    }

    //获取图中顶点的个数
    public int getvCount(){
        return vCount;
    }

    //获取图中边的个数
    public int getEdgeCount(){
        return edgeCount;
    }

    //显示邻接矩阵表示的图
    public void print(){
        for (int[] o : matrix) {
            for (int i : o) {
                System.out.print(i+"  ");
            }
            System.out.println();
        }
    }

    //得到第一个邻接节点的下标
    public int getFirst(int v){
        for (int i = 0; i < vertex.size(); i++) {
            if(matrix[v][i] == 1){
                return i;
            }
        }
        return -1;
    }

    //根据前一个邻接节点的下标获取下一个邻接节点
    public int getNext(int v1,int v2){
        for(int i = v2+1; i < vertex.size(); i++){
            if(matrix[v1][i] == 1){
                return i;
            }
        }
        return -1;
    }


    public void dfs(){
        //遍历所有的节点，进行dfs回溯
        for (int i = 0; i < getvCount(); i++) {
            //判断顶点是否被搜索
            if(!mark[i]){
                dfs2(i);
            }
        }
        //复原标记辅组数组的状态
        for (int i = 0; i < mark.length; i++) {
            mark[i] = false;
        }
    }

    public void dfs( int v){
        System.out.print(vertex.get(v)+"——>");
        //将vertex标记为已经搜索
        mark[v] = true;
        int w = getFirst(v);
        while (w != -1){
            if(!mark[w]){
                dfs(w);
            }
            w = getNext(v,w);
            //相通的顶点+1
        }
    }


    public void dfs2(int v){
        mark[v] = true;
        System.out.print(vertex.get(v)+"——>");
        for (int i = 0; i < vertex.size(); i++) {
            if(matrix[v][i] == 1 && !mark[i]){
                dfs(i);
            }
        }
    }


    public void bfs(){
        for (int i = 0; i < vertex.size(); i++) {
            if(!mark[i]){
                bfs(i);
            }
        }

        for (int i = 0; i < mark.length; i++) {
            mark[i] = false;
        }
    }

    //深度优先遍历
    public void bfs(int v){
        Queue queue = new LinkedList();
        int next;
        mark[v] = true;
        queue.add(v);
        while (!queue.isEmpty()){
            next = (int) queue.remove();
            System.out.print(vertex.get(next)+"——>");
            int vex = getFirst(next);
            while (vex != -1){
                if(mark[vex] == false){
                    queue.add(vex);
                    mark[vex] = true;
                }
                vex = getNext(next,vex);
            }
        }
    }

}

