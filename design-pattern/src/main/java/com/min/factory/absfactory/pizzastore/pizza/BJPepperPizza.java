package com.min.factory.factorymethod.pizzastore.pizza;

public class BJPepperPizza extends Pizza{
    @Override
    public void prepare() {
        setName("bj pepper");
        System.out.println("bj pepper pizza");
    }
}
