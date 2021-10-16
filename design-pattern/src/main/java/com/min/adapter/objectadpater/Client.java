package com.min.adapter.objectadpater;

public class Client {
    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.charge(new VoltageAdapter(new Voltage220()));
    }
}
