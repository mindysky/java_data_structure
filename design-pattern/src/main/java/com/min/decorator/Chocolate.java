package com.min.decorator;

public class Chocolate extends Decorator{
    public Chocolate(Drink obj) {
        super(obj);
        setDes("chocolate");
        setPrice(3.0f);
    }
}
