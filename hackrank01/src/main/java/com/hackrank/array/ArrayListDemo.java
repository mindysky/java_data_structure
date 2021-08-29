package com.hackrank.array;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ArrayListDemo {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        String str = "hello";
        sha256.update(str.getBytes());
        byte[] digest = sha256.digest();
        for(byte b: digest){
            System.out.format("%02x",b);
        }

    }
}
