package com.min.factory.absfactory.pizzastore.pizza;

import com.min.factory.absfactory.pizzastore.pizza.Pizza;

public class LDCheesePizza extends Pizza {
    @Override
    public void prepare() {
        setName("ld cheese");
        System.out.println("LD cheese pizza");
    }
}
