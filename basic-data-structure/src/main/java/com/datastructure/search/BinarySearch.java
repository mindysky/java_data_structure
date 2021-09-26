package com.datastructure.search;

import java.util.ArrayList;
import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};

//        int resIndex = binarySearch(arr, 0, arr.length-1, 88);
//        System.out.println("resIndex==" + resIndex);

        int[] arr2 = {1, 8, 10, 89, 89, 89, 89, 1000, 1234};
        List<Integer> indexList = binarySearchAll(arr2, 0, arr2.length - 1, 89);

        System.out.println("索引集合==" + indexList);
    }


    /*
     * 1. 找到mid索引值，不要马上返回
     * 2. 向mid索引值得左边扫描，将所有满足条件的元素的下标加入到集合ArrayList
     * 3. 向mid索引值的右边扫描，  将所有满足条件的元素的下标加入到集合ArrayList
     * 4. 将arrayList 返回
     * */
    public static ArrayList<Integer> binarySearchAll(int[] arr, int left, int right, int findVal) {
        if (left > right) {
            return new ArrayList<>();
        }

        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (findVal > midVal) {  //向右递归
            return binarySearchAll(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            return binarySearchAll(arr, left, mid - 1, findVal);
        } else {

            ArrayList<Integer> resIndexList = new ArrayList<Integer>();
            //向mid索引值的左边扫描，将所有满足条件的下标，加入到集合arraylist
            int temp = mid - 1;
            while (true) {
                if (temp < 0 || arr[temp] != findVal) {
                    break;
                }
                resIndexList.add(temp);
                temp -= 1; //temp左移
            }
            resIndexList.add(mid);
            //向mid索引值的右边扫描，将所有满足条件的下标，加入到集合arraylist
            temp = mid + 1;
            while (true) {
                if (temp > arr.length - 1 || arr[temp] != findVal) {
                    break;
                }
                resIndexList.add(temp);
                temp += 1;//temp右移
            }
            return resIndexList;
        }

    }


    /**
     * @param arr     数组
     * @param left    左边索引
     * @param right   右边索引
     * @param findVal 要查找的值
     * @return
     */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {

        if (left > right) return -1;

        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (findVal > midVal) {  //向右递归
            return binarySearch(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            return binarySearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }

    }

}
