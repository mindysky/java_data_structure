package com.min.factory.factorymethod.pizzastore.pizza;

public class LDCheesePizza extends Pizza {
    @Override
    public void prepare() {
        setName("ld cheese");
        System.out.println("LD cheese pizza");
    }
}
