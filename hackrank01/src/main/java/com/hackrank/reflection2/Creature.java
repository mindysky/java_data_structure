package com.hackrank.reflection2;

import java.io.Serializable;

public class Creature<T> implements Serializable {

    private  char gender;
    public double weight;

    private void  breath(){
        System.out.println("breath...");
    }

    public void eat(){
        System.out.println("creature eat....");
    }
}
