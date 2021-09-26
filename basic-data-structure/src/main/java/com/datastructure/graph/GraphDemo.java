package com.datastructure.graph;

import java.util.ArrayList;

public class GraphDemo {
    private ArrayList<String> vertexList; //存储顶点集合
    private int[][] edges; //存储图对应的邻接矩阵
    private int numOfEdges; //表示边的数目

    public static void main(String[] args) {

    }

    public GraphDemo(int n){
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0;
    }

    //insert  vertex
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    //add edges
    public void insertEdge(int v1, int v2, int weight){

    }

}
