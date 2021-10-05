package com.datastructure.algorithm;

import java.util.Arrays;
import java.util.Queue;

public class Prim {
    public static void main(String[] args) {
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verxs = data.length;
        //邻接矩阵的关系使用二维数组表示，10000这个数比较大，表示两个点不联通
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000}
        };

        MGraph mGraph = new MGraph(verxs);

        MinTree minTree = new MinTree();
        minTree.createGraph(mGraph, verxs, data, weight);

//        minTree.showGraph(mGraph);

        minTree.prim(mGraph,0);

    }
}

//创建最小生成树
class MinTree {
    //创建图的邻接矩阵

    /**
     * @param graph  图对象
     * @param verxs  图对应的顶点个数
     * @param data   图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph graph, int verxs, char[] data, int[][] weight) {
        int i, j;
        for (i = 0; i < verxs; i++) { //顶点
            graph.data[i] = data[i];
            for (j = 0; j < verxs; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    public void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * @param graph
     * @param v     表示从图的第几个顶点开始生成
     */
    public void prim(MGraph graph, int v) {
        int[] visited = new int[graph.verxs];
        visited[v] = 1;
        //记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000; //init a max weight, will be replaced in recursion
        for (int k = 1; k < graph.verxs; k++) { //because there is a graph.verxs vertex
            for (int i = 0; i < graph.verxs; i++) {
                for (int j = 0; j < graph.verxs; j++) {
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            //找到最小的一条边
            System.out.println(graph.data[h1] + "---" + graph.data[h2] + "---" + minWeight);
            //标记当前节点已访问
            visited[h2] = 1;
            minWeight = 10000;
        }
    }
}

class MGraph {
    int verxs; //表示图的节点个数
    char[] data; //存放节点数据
    int[][] weight; //存放边，就是我们的邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }
}