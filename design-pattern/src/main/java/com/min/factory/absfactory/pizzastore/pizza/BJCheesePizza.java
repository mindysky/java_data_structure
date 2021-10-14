package com.min.factory.factorymethod.pizzastore.pizza;

public class BJCheesePizza extends Pizza {
    @Override
    public void prepare() {
        setName("bj cheese");
        System.out.println("bj cheese pizza");
    }
}
