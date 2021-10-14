package com.min.factory.simplefactory.pizzastore.order;

import com.min.factory.simplefactory.pizzastore.pizza.CheesePizza;
import com.min.factory.simplefactory.pizzastore.pizza.GreekPizza;
import com.min.factory.simplefactory.pizzastore.pizza.Pizza;

public class SimpleFactory {
    public Pizza createPizza(String orderType) {
        Pizza pizza = null;
        System.out.println("simple factory model");
        if (orderType.equals("greek")) {
            pizza = new GreekPizza();
            pizza.setName(orderType);
        } else if (orderType.equals("cheese")) {
            pizza = new CheesePizza();
            pizza.setName(orderType);
        }
        return pizza;
    }

    public static Pizza createPizza2(String orderType) {
        Pizza pizza = null;
        System.out.println("simple factory model");
        if (orderType.equals("greek")) {
            pizza = new GreekPizza();
            pizza.setName(orderType);
        } else if (orderType.equals("cheese")) {
            pizza = new CheesePizza();
            pizza.setName(orderType);
        }
        return pizza;
    }
}
