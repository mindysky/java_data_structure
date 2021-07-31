package com.hackrank.rank;

import java.math.BigInteger;
import java.util.Scanner;

public class BigIntegerTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BigInteger a = new BigInteger(sc.next());
        BigInteger  b = new BigInteger(sc.next());

        BigInteger  bi3, bi4;
        bi3 = a.add(b);
        bi4 = a.multiply(b);

        System.out.println(bi3);
        System.out.println(bi4);
    }
}
