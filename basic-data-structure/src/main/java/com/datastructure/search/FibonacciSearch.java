package com.datastructure.search;

import java.util.Arrays;

public class FibonacciSearch {
    public static int maxSize = 20;

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};

        int index = fibSearch(arr,1234);

        System.out.println("index===="+index);

    }

    //斐波那契数列 mid = low+F(k-1)-1;
    //先获取斐波那契数列
    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }


    //编写斐波那契查找算法
    public static int fibSearch(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;
        int k = 0; //表示斐波那契分割数值的下标
        int mid = 0; //存放mid
        int f[] = fib(); //获取斐波那契数列

        //获取斐波那契分割数值的下标
        while (high > f[k] - 1) {
            k++;
        }

        //因为f[k] 值可能大于arr的长度，因此我们需要使用Arrays类，构造一个新的数组，并指向arr[]
        //不足的部分会使用0填充
        int[] temp = Arrays.copyOf(arr, f[k]);
        //实际上需求使用arr数组最后的数值填充temp
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        while (low <= high) {
            mid = low + f[k - 1] - 1;
            if (key < temp[mid]) {//向左查找
                high = mid - 1;
                k--;
            } else if (key > temp[mid]) { //向右查找
                low = mid + 1;
                k -= 2;
            } else {//找到
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        return -1;
    }

}
