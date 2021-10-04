package com.datastructure.algorithm;

import sun.security.util.Length;

import java.util.Arrays;

public class DynamicProgram {
    public static void main(String[] args) {
        int[] w = {1, 4, 3};
        int[] val = {1500, 3000, 2000};
        int m = 4; //knapsack capacity
        int n = val.length; //item count

        //create arr
        //v[i][j]  表示前i个物品中能够装入容量为j的背包内的最大价值
        int[][] v = new int[n + 1][m + 1];

        int[][] path = new int[n + 1][m + 1];

        //初始化第一行和第一列
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;  //将第一列设置为0
        }
        //将第一行设置为0
        Arrays.fill(v[0], 0);

        //根据前面得到的公式来动态规划处理
        for (int i = 1; i < v.length; i++) {
            for (int j = 1; j < v[0].length; j++) {
                if (w[i - 1] > j) {
                    v[i][j] = v[i - 1][j];
                } else {
                   // v[i][j] = Math.max(v[i - 1][j], val[i - 1] + v[i - 1][j - w[i - 1]]);
                    //为了记录商品存放到背包的情况，我们不能直接使用上面的公式
                    if (v[i - 1][j] < val[i - 1] + v[i - 1][j - w[i - 1]]) {
                        v[i][j] = val[i - 1] + v[i - 1][j - w[i - 1]];
                        //把当前情况记录到path
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i - 1][j];
                    }
                }
            }
        }

        //输出v
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                System.out.println(v[i][j] + " ");
            }
            System.out.println();
        }

        //输出放入的商品
        int i = path.length - 1;  //行的最大下标
        int j = path[0].length - 1; //列的最大下标
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品加入到背包\n", i);
                j -= w[i - 1];
            }
            i--;
        }
    }
}
