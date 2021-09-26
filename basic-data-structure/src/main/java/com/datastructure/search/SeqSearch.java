package com.datastructure.search;

public class SeqSearch {
    public static void main(String[] args) {
        int[] arr = {1, 9, 11, -1, 34, 89};

        int index = seqSearch(arr,1);

        System.out.println(index);

    }

    public static int seqSearch(int[] arr, int value){
        //线性查找是逐一查找，发现有相同的值，就返回下标，否则返回下标
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]==value){
                return i;
            }
        }
        return -1;
    }

}
