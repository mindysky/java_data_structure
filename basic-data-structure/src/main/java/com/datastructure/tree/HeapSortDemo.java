package com.datastructure.tree;

import java.util.Arrays;

public class HeapSortDemo {
    public static void main(String[] args) {
        int arr[] = {4, 6, 8, 5, 9};
        heapSort(arr);
    }

    //create heapsort method
    public static void heapSort(int[] arr) {
        int temp = 0;

        //分布完成
        //将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        /*
         * 将堆顶元素与末尾元素交换，将最大元素“沉”到数组末端
         * 重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+ 交换步骤，知道整个序列有序
         * */

        for (int j = arr.length - 1; j > 0; j--) {
            //交换
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, j);

        }
        System.out.println("first time" + Arrays.toString(arr));


    }
    //将一个数组(二叉树),调整成一个大顶堆

    /**
     * 功能： 完成 将 以 i 对应的非叶子结点的树调整成大顶堆
     *
     * @param arr    待调整的数组
     * @param i      表示非叶子结点在数组中的索引
     * @param length 对多少个元素进行调整
     */
    public static void adjustHeap(int arr[], int i, int length) {
        int temp = arr[i]; //先取出当前元素的值，保存在临时变量
        //开始调整
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++;
            }
            if (arr[k] > temp) { //如果
                arr[i] = arr[k];  //把较大的值赋给当前结点
                i = k;
            } else {
                break;
            }
        }
        //当for 循环结束后，我们已经将以i为父结点的树的最大值，放在了最顶（局部）
        arr[i] = temp; //将temp值放到调整后的位置

    }
}
