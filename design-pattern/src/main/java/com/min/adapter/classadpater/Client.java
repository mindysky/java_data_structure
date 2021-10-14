package com.min.adapter.classadpater;

import com.sun.scenario.effect.impl.prism.ps.PPSBlend_HARD_LIGHTPeer;

public class Client {
    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.charge(new VoltageAdapter());
    }
}
