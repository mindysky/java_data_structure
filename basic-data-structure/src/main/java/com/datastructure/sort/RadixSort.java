package com.datastructure.sort;

import java.util.Arrays;

public class RadixSort {
    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};

        radixSort(arr);
        System.out.println("基数排序---" + Arrays.toString(arr));

    }

    //基数排序方法
    public static void radixSort(int[] arr) {

        //得到数组中最大的数的位数
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        int maxLength = (max + "").length();

        /*
         * 1. 二维数组包含10个一维数组
         * 2. 为了防止放入数的时候，数据溢出，则每个一维数组（桶），大小定为arr.length
         * 3. 基数排序是使用空间换时间的经典算法
         * */
        int[][] bucket = new int[10][arr.length];

        //为了记录每个桶中，实际存放了多少个数据，我们定义一个一维数组来记录每个桶的每次放入的数据个数
        int[] bucketElementCounts = new int[10];
        for (int i = 0, n = 1; i < maxLength; i++,n*=10) {
            for (int j = 0; j < arr.length; j++) {
                //取出每个元素的个位的值
                int digitOfElement = arr[j] /n % 10;

                //放入对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
            //按照桶的顺序取出元素，放到原来数组
            int index = 0;
            for (int k = 0; k < bucketElementCounts.length; k++) {
                //如果桶中有数据，我们才放入数组
                if (bucketElementCounts[k] != 0) {
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        arr[index++] = bucket[k][l];
                    }
                }
                bucketElementCounts[k] = 0;
            }

            System.out.println("第"+(i+1)+"次" + Arrays.toString(arr));
        }


    }
}
