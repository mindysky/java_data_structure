package com.hackrank.rank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PrimalityTestTest {

    @Test
    void testPrime() {
        PrimalityTest primalityTest = new PrimalityTest();
        BigInteger bn = new BigInteger(String.valueOf(7));
        Boolean isPrime = primalityTest.testPrime(bn);
        Assertions.assertTrue(isPrime);

    }
}