package com.datastructure.search;

import java.util.Arrays;

public class InsertValueSearch {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i <100 ; i++) {
            arr[i] = i+1;
        }

        System.out.println(Arrays.toString(arr));

        int index = insertValueSearch(arr,0, arr.length-1,10);
        System.out.println("index======"+index);
    }

    /**
     *
     * @param arr  数组
     * @param left  左边索引
     * @param right  右边索引
     * @param findVal
     * @return   如果找到就返回对应的下标， 如果没有找到返回 -1；
     */
    public static int insertValueSearch(int[] arr, int left, int right, int findVal){
        //findVal<arr[0]||findVal>arr[arr.length-1]  必须加，否则mid 可能越界
        if(left>right||findVal<arr[0]||findVal>arr[arr.length-1]){
            return -1;
        }

        int mid = left+(right-left)*(findVal-arr[left])/(arr[right]-arr[left]);
        int midVal = arr[mid];
        if(findVal>midVal){ //向右递归查找
            return insertValueSearch(arr,mid+1,right,findVal);
        }else if(findVal<midVal){ //向左递归查找
            return insertValueSearch(arr,left,mid-1,findVal);
        }else{
            return mid;
        }

    }
}
