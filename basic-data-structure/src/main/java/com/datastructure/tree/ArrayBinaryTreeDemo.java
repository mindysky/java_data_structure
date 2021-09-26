package com.datastructure.tree;

public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};


        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
        arrayBinaryTree.preOrder();


    }
}


//creat ArrayBinaryTree to implement list binary tree

class ArrayBinaryTree {
    private int[] arr;

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    //重载
    public void preOrder(){
        this.preOrder(0);
    }

    public void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("arr is empty--------");
        }
        System.out.println("index ==== "+arr[index]);
        //Recursive to the left
        if ((index * 2 + 1) < arr.length) {
            preOrder(2 * index + 1);
        }
        //Recursive to the right
        if((index*2+2)< arr.length){
            preOrder(2*index+2);
        }

    }

}