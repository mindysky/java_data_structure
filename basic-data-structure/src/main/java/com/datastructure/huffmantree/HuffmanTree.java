package com.datastructure.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node node = createHuffmanTree(arr);

        preOrder(node);

    }

    //编写一个前序遍历的方法
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("empty tree");
        }
    }

    //create huffman tree

    /**
     * @param arr 需要创建成赫夫曼树的数组
     * @return 创建好后的赫夫曼树的root 节点
     */
    public static Node createHuffmanTree(int[] arr) {
        //1.遍历arr
        //2. 将arr的每个元素构成一个Node
        //3. 将Node放入到ArrayList

        List<Node> nodes = new ArrayList<Node>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }

        while (nodes.size() > 1) {

            Collections.sort(nodes);

            System.out.println(nodes);

            //取出根节点权值最小的两棵二叉树

            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);

            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;

            //从ArrayList中删除用过的结点
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parent);

        }
        //return huffman tree's root node
        return nodes.get(0);
    }
}

//create node
//为了让Node对象持续排序 collections 集合排序
//让Node 实现comparable接口
class Node implements Comparable<Node> {
    int value; //节点权值
    Node left; //point to left child node
    Node right; //point to right child node

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    public Node(int value) {
        this.value = value;
    }

    //sort


    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        //从小到大排列
        return this.value - o.value;
    }
}