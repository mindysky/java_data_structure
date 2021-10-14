package com.min.prototype;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws CloneNotSupportedException {
//        Sheep sheep = new Sheep("tom", 1, "red");
//        Sheep sheep2 = (Sheep) sheep.clone();
//        Sheep sheep3 = (Sheep) sheep.clone();
//        Sheep sheep4 = (Sheep) sheep.clone();
//        Sheep sheep5 = (Sheep) sheep.clone();
//
//        System.out.println(sheep);
//        System.out.println(sheep2);
//        System.out.println(sheep3);
//        System.out.println(sheep4);
//        System.out.println(sheep5);

        DeepProtoType deepProtoType = new DeepProtoType();
        deepProtoType.name = "aaa";
        deepProtoType.deepClonableTarget = new DeepClonableTarget("bbb","class");

        //deep copy
        DeepProtoType clone = (DeepProtoType) deepProtoType.deepClone();
        System.out.println(clone.equals(deepProtoType));
    }
}
