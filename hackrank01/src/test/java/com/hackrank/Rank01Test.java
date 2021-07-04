package com.hackrank;

import com.hackrank.rank.Rank01;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Rank01Test {
    @Test
    public void TestRank01(){
        int n = 3;
        Rank01 rank01 = new Rank01();
        String actualResult = rank01.getAns(3);

        assertEquals("Weird", actualResult);

    }

}