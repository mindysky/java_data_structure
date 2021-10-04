package com.datastructure.algorithm;

public class HanoitTower {
    public static void main(String[] args) {
       hanoitTower(5,'A','B','C');
    }

    public static void hanoitTower(int num, char a, char b, char c) {
        //if one dish
        if (num == 1) {
            System.out.println("第一个盘从" + a + "到" + c);
        } else {
            //n>=2, 我们总是可以看做是两个盘， 1. 最下的盘，2.上面的所有盘
            //1.先把最上面的盘A->B, 移动过程用到C塔
            hanoitTower(num - 1, a, c, b);
            //2.把最下面的盘 A->C
            System.out.println("第" + num + "个盘从" + a + "到" + c);
            //3. 把B塔的所有盘从B-C，移动过程用到A 塔
            hanoitTower(num - 1, b, a, c);
        }
    }
}
