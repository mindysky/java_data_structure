package com.min.decorator;

public class Milk extends Decorator{
    public Milk(Drink obj) {
        super(obj);
        setDes("milk");
        setPrice(5.0f);
    }
}
