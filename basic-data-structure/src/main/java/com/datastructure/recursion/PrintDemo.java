package com.datastructure.recursion;

public class PrintDemo {
    public static void main(String[] args) {
//        test(10);
        int res = factorial(4);
        System.out.println("factorial=="+res);
    }

    public static void test(int n){
        if(n>2){
            test(n-1);
        }
        System.out.println("n=="+n);
    }


    public static int factorial(int n){
        if(n==1){
            return 1;
        }else {
            return factorial(n-1)*n;
        }
    }
}
