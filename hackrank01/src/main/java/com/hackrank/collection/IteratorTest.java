package com.hackrank.collection;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class IteratorTest {
    public static void main(String[] args) {
        Collection<java.io.Serializable> coll = new ArrayList<java.io.Serializable>();
        coll.add(123);
        coll.add(456);
        coll.add(new String("yyy"));
        coll.add(false);

        for (Serializable serializable : coll) {
            System.out.println(serializable);
        }




    }
}
