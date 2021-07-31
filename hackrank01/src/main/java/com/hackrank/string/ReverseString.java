package com.hackrank.string;

public class ReverseString {
    public static void main(String[] args) {
        String A = "ABCD";
        System.out.println(A.equals(new StringBuilder(A).reverse().toString())?"Yes":"No");
    }
}
