package com.min.adapter.classadpater;

public class VoltageAdapter extends Voltage220 implements Voltage5{
    @Override
    public int output5() {
        int src = output220V();
        int dstV = src/44;
        System.out.println("adjust to " + dstV);
        return dstV;
    }
}

