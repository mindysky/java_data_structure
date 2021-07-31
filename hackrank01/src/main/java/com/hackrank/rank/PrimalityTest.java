package com.hackrank.rank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class PrimalityTest {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String n = bufferedReader.readLine();
        BigInteger bn = new BigInteger(n);

        testPrime(bn);

        bufferedReader.close();


    }
    public static Boolean testPrime(BigInteger n){
        Boolean s = n.isProbablePrime(1);
        System.out.println(s?"prime":"not prime");
        return s;
    }

}
