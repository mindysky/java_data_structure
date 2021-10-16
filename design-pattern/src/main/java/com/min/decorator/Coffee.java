package com.min.decorator;

public abstract class Coffee extends Drink {
    @Override
    public float cost() {
        return super.getPrice();
    }
}
