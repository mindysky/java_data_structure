package com.datastructure.algorithm.floyd;

import java.util.Arrays;

public class FloydAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        final int N = 65535;
        int[][] matrix = {
                {0, 5, 7, N, N, N, 2},
                {5, 0, N, 9, N, N, 3},
                {7, N, 0, N, 8, N, N},
                {N, 9, N, 0, N, 4, N},
                {N, N, 8, N, 0, 5, 4},
                {N, N, N, 4, 5, 0, 6},
                {2, 3, N, N, 4, 6, 0}};
        Graph graph = new Graph(vertex.length, matrix, vertex);
        graph.floyd();
        graph.show();
    }
}


class Graph {
    private char[] vertex; //顶点数组
    private int[][] dis; //保存从各个顶点出发到其他顶点的距离，最后的结果
    private int[][] pre;  //保存到达目标顶点的前驱顶点

    public Graph(int length, int[][] matrix, char[] vertex) {
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[length][length];

        for (int i = 0; i < length; i++) {
            Arrays.fill(pre[i], i);
        }
    }

    //show pre and dis
    public void show() {
        for (int i = 0; i < dis.length; i++) {
            //show pre
            for (int j = 0; j < dis.length; j++) {
                System.out.print(pre[i][j] + " ");
            }
            System.out.println("");

        }
        System.out.println("========");
        for (int i = 0; i < dis.length; i++) {
            for (int k = 0; k < dis.length; k++) {
                System.out.printf("(%c -> %c) %-6d ", vertex[i], vertex[k], dis[i][k]);
            }
            System.out.println("");
        }
    }

    public void floyd() {
        int len = 0; //变量保存距离
        //对中间顶点的遍历,k就是中间顶点的下标
        for (int k = 0; k < dis.length; k++) {
            for (int i = 0; i < dis.length; i++) {
                for (int j = 0; j < dis.length; j++) {
                    len = dis[i][k] + dis[k][j]; //从i顶点出发，经过K中间顶点，到达j顶点的距离
                    if (len < dis[i][j]) {
                        //如果len小于i到j直连的距离
                        dis[i][j] = len;  //更新距离
                        pre[i][j] = pre[k][j]; //更新前驱顶点
                    }
                }
            }
        }

    }

}