package com.datastructure.sort;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1, -1, 90};

        insertSort(arr);
    }

    public static void insertSort(int[] arr) {
        int insertVal = 0;
        int insertIndex = 0;

        for (int i = 1; i < arr.length; i++) {
            insertVal = arr[i];
            insertIndex = i - 1;

            //给insertVal找插入位置
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            //这里判断是否需要赋值
            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertVal;
            }

            System.out.println("第" + i + "次sorting");
            System.out.println(Arrays.toString(arr));
        }


    }
}
