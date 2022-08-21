package com.example.demoproject.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemoFilterTest {
    @Test
    public void addTest(){
        String abc = new DemoFilter().add("abc");
        System.out.println(abc);
    }
}