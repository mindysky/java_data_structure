package com.min.factory.absfactory.pizzastore.pizza;
import com.min.factory.absfactory.pizzastore.pizza.Pizza;

public class BJCheesePizza extends Pizza {
    @Override
    public void prepare() {
        setName("bj cheese");
        System.out.println("bj cheese pizza");
    }
}
