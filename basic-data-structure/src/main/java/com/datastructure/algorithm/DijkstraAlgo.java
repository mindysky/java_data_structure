package com.datastructure.algorithm;

import java.util.Arrays;

public class DijkstraAlgo {
    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        final int N = 65535;
        int[][] matrix = {
                {N, 5, 7, N, N, N, 2},
                {5, N, N, 9, N, N, 3},
                {7, N, N, N, 8, N, N},
                {N, 9, N, N, N, 4, N},
                {N, N, 8, N, N, 5, 4},
                {N, N, N, 4, 5, N, 6},
                {2, 3, N, N, 4, 6, N}};
        Graph graph = new Graph(vertex, matrix);
        graph.showGraph();
        graph.dijkstra(2);
        graph.showDijkstra();
    }

}

class VisitedVertex {
    public int[] already_arr;
    public int[] pre_visited;
    public int[] dis;

    /**
     * @param length： 表示顶点个数
     * @param index：  出发顶点对应的下标
     */
    public VisitedVertex(int length, int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];
        this.already_arr[index] = 1;//设置出发顶点被访问过
        Arrays.fill(dis, 65535);
        this.dis[index] = 0;

    }

    /**
     * 判断index顶点是否被访问过
     *
     * @param index
     * @return 如果访问过就返回true, 否则返回false
     */
    public boolean in(int index) {
        return already_arr[index] == 1;
    }

    /**
     * 更新出发顶点到index顶点的距离
     *
     * @param index
     * @param len
     */
    public void updateDis(int index, int len) {
        dis[index] = len;
    }

    /**
     * 更新pre这个顶点到前驱顶点为index顶点
     *
     * @param pre
     * @param index
     */
    public void updatePre(int pre, int index) {
        pre_visited[pre] = index;
    }

    /**
     * @param index
     * @return
     */
    public int getDis(int index) {
        return dis[index];
    }

    /**
     * 继续选择并返回新的访问顶点,比如这里的G访问后，就是A作为新的访问顶点
     *
     * @return
     */
    public int updateArr() {
        int min = 65535, index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            if (already_arr[i] == 0 && dis[i] < min) {
                min = dis[i];
                index = i;
            }
        }
        //更新index顶点被访问过
        already_arr[index] = 1;
        return index;
    }

    public void show() {

        for (int i : already_arr) {
            System.out.print(i + " ");
        }
        System.out.println("================");
        for (int i : dis) {
            System.out.print(i + " ");
        }
        System.out.println("================");
        for (int i : pre_visited) {
            System.out.print(i + " ");
        }
        System.out.println("================");

        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int count = 0;
        for (int i : dis) {
            if (i != 65535) {
                System.out.print(vertex[count] + "(" + i + ")");
            } else {
                System.out.println("N");
            }
            count++;
        }

    }
}


class Graph {
    private char[] vertex; //顶点数组
    private int[][] matrix; //邻居矩阵
    private VisitedVertex vv; //已经访问的顶点集合

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    public void showDijkstra() {
        vv.show();
    }

    public void showGraph() {
        for (int[] link : matrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    //Dijkstra Algorithm

    /**
     * @param index 表示出发顶点对应的下标
     */
    public void dijkstra(int index) {
        vv = new VisitedVertex(vertex.length, index);
        update(index); //更新index顶点到周围顶点的距离和前驱顶点
        for (int j = 1; j < vertex.length; j++) {
            index = vv.updateArr(); //选择并返回新的访问顶点
            update(index);  //更新index顶点到周围顶点的距离和前驱顶点
        }
    }


    //更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
    private void update(int index) {
        int len = 0;
        for (int j = 0; j < matrix[index].length; j++) {
            //len: 出发顶点到index顶点的距离 + 从index顶点到j顶点的距离的和
            len = vv.getDis(index) + matrix[index][j];
            //如果j顶点没有被访问过，并且len小于出发顶点到j顶点的距离，就需要更新
            if (!vv.in(j) && len < vv.getDis(j)) {
                vv.updatePre(j, index);  //更新j顶点到前驱inde顶点
                vv.updateDis(j, len);  //更新出发顶点到j顶点的距离
            }
        }
    }

}