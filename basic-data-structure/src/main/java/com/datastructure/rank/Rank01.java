package com.datastructure.rank;

import java.util.Scanner;

public class Rank01 {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getAns(int n){

        String ans;
        if(n%2 == 1 || n>= 6 && n<=20){
            ans = "Weird";
        }
        else if (n%2 == 0 || n >= 2 && n <= 5){
            ans = "Not Weird";
        }
        else{
            ans = "Not Weird";
        }
        System.out.println(ans);
        return ans;

    }


    public static void main(String[] args) {

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        getAns(n);
        scanner.close();


    }

}



