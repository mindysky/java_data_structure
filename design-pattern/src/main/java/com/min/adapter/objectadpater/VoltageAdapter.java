package com.min.adapter.objectadpater;

import com.min.adapter.objectadpater.Voltage220;

public class VoltageAdapter implements Voltage5 {
    public Voltage220 voltage220;

    public VoltageAdapter(Voltage220 voltage220) {
        this.voltage220 = voltage220;
    }

    @Override
    public int output5() {
        int dst = 0;
        if (null != voltage220) {
            int src = voltage220.output220V();
            dst = src / 44;
        }
        System.out.println("adjust to " + dst);
        return dst;
    }
}
