package com.datastructure.sort;

import java.util.Arrays;

public class SelectSort {
    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1};

        selectSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public static void selectSort(int[] arr) {
        //算法: 先简单再复杂， 复杂算法拆分成简单问题再逐步解决

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];

            for (int j = i+1; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }

        }


    }
}
