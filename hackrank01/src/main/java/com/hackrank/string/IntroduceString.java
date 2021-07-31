package com.hackrank.string;

public class IntroduceString {
    public static void main(String[] args) {
        String A = "";
        String B = "";
        System.out.println(A.length()+B.length());
        System.out.println(A.compareTo(B)>0?"YES":"NO");
        String a1 = A.substring(0,1).toUpperCase()+A.substring(1);
        String b1 = B.substring(0,1).toUpperCase()+B.substring(1);
        System.out.println(a1+" "+b1);
    }
}
