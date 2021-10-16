package com.min.bridge;

public class Xiaomi implements Brand{
    @Override
    public void call() {
        System.out.println("xiaomi call");
    }

    @Override
    public void open() {
        System.out.println("xiaomi open");
    }

    @Override
    public void close() {
        System.out.println("xiaomi close");
    }
}
