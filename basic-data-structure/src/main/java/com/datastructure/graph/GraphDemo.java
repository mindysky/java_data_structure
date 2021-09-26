package com.datastructure.graph;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphDemo {
    private ArrayList<String> vertexList; //存储顶点集合
    private int[][] edges; //存储图对应的邻接矩阵
    private int numOfEdges; //表示边的数目

    public static void main(String[] args) {
        int n = 5;
        String[] Vertexs = {"A","B","C","D","E"};
        GraphDemo graphDemo = new GraphDemo(n);

        //add vertex
        for(String vertex:Vertexs){
            graphDemo.insertVertex(vertex);
        }

        //add edges
        graphDemo.insertEdge(0,1,1); //A-B
        graphDemo.insertEdge(0,2,1); //A-C
        graphDemo.insertEdge(1,2,1); //B-C
        graphDemo.insertEdge(1,3,1); //B-D
        graphDemo.insertEdge(1,4,1); //B-E

        graphDemo.showGraph();

    }

    public GraphDemo(int n){
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0;
    }

    public int getNumOfVertex(){
        return vertexList.size();
    }

    public int getNumOfEdges(){
        return numOfEdges;
    }

    //显示图对应的矩阵
    public void showGraph(){
        for(int[] link: edges){
            System.out.println(Arrays.toString(link));
        }
    }


    //返回节点i 对应的数据
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    //返回v1 和 v2的权值
    public int getWeight(int v1, int v2){
        return edges[v1][v2];
    }

    //insert  vertex
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    //add edges

    /**
     *
     * @param v1   表示点的下标即使第几个顶点  “A” -“B”  “A” -》0  “B” -》1
      * @param v2    第二个顶点对应的下标
     * @param weight
     */
    public void insertEdge(int v1, int v2, int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

}
