package com.min.factory.absfactory.pizzastore.pizza;

import com.min.factory.absfactory.pizzastore.pizza.Pizza;

public class LDPepperPizza extends Pizza {
    @Override
    public void prepare() {
        setName("ld pepper");
        System.out.println("LD pepper pizza");
    }
}
