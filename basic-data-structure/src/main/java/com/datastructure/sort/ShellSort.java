package com.datastructure.sort;

import java.util.Arrays;

public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 7, 6, 3, 5, 4, 2, 0};
        shellSort2(arr);
    }

    //希尔排序，交换法
    public static void shellSort(int[] arr) {
        int temp = 0;
        //希尔排序
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }

        }

        System.out.println(Arrays.toString(arr));
    }

    //对希尔排序的优化，移位法
    public static void shellSort2(int[] arr) {
        //希尔排序
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        //移动
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    //当退出while循环后，就给temp找到插入位置
                    arr[j] = temp;
                }
            }

        }

        System.out.println(Arrays.toString(arr));
    }
}
