package com.min.factory.factorymethod.pizzastore.order;

import com.min.factory.factorymethod.pizzastore.pizza.BJCheesePizza;
import com.min.factory.factorymethod.pizzastore.pizza.BJPepperPizza;
import com.min.factory.factorymethod.pizzastore.pizza.Pizza;

public class BJOrderPizza extends OrderPizza{
    @Override
    Pizza createPizza(String ordertype) {
        Pizza pizza = null;
        if(ordertype.equals("cheese")){
            pizza = new BJCheesePizza();
        }else if(ordertype.equals("pepper")){
            pizza = new BJPepperPizza();
        }
        return pizza;
    }
}
