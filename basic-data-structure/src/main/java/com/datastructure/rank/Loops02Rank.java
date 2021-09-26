package com.datastructure.rank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loops02Rank {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(bufferedReader.readLine().trim());
        for (int i =1;i<11;i++){
            int sum = N*i;
            System.out.println(N+" x "+ i +" = " + sum);
        }


        bufferedReader.close();
    }
}
