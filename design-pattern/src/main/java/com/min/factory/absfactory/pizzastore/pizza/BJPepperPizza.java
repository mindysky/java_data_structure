package com.min.factory.absfactory.pizzastore.pizza;

import com.min.factory.absfactory.pizzastore.pizza.Pizza;

public class BJPepperPizza extends Pizza {
    @Override
    public void prepare() {
        setName("bj pepper");
        System.out.println("bj pepper pizza");
    }
}
