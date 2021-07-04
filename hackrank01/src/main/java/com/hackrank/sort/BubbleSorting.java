package com.hackrank.sort;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class BubbleSorting {
    public static void main(String[] args) {
        int arr[] = {3, 9, -1, 10, 20};
        System.out.println(Arrays.toString(arr));
        bubbleSort(arr);

        System.out.println("排序");
        System.out.println(Arrays.toString(arr));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    }

    public static void bubbleSort(int[] arr){
        int temp = 0; //temp variable
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            if(!flag){
                break;
            }else{
                flag = false;
            }
        }
    }
}
