package com.min.decorator;

public class Soy extends Decorator{
    public Soy(Drink obj) {
        super(obj);
        setDes("soy");
        setPrice(2.0f);
    }
}
