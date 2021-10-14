package com.min.factory.absfactory.pizzastore.order;


import com.min.factory.absfactory.pizzastore.pizza.Pizza;

//抽象工厂模式的抽象层
public interface AbsFactory {
    public Pizza createPizza(String orderType);
}
