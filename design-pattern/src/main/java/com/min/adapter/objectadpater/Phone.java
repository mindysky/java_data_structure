package com.min.adapter.objectadpater;

import com.min.adapter.objectadpater.Voltage5;

public class Phone {
    public void charge(Voltage5 voltage5) {
        if (voltage5.output5() == 5) {
            System.out.println("start charge");
        } else if (voltage5.output5() > 5) {
            System.out.println("can not charge");
        }
    }
}
