package com.datastructure.rank;
import java.io.*;

import static java.util.stream.Collectors.joining;

public class LoopsRank {
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
