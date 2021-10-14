package com.min.factory.factorymethod.pizzastore.pizza;

public class LDPepperPizza extends Pizza{
    @Override
    public void prepare() {
        setName("ld pepper");
        System.out.println("LD pepper pizza");
    }
}
