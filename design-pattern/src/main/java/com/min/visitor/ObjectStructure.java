package com.min.visitor;

import com.min.factory.absfactory.pizzastore.pizza.Pizza;
import sun.text.resources.cldr.lag.FormatData_lag;

import java.util.LinkedList;
import java.util.List;

public class ObjectStructure {
    //maintain a list
    private List<Person> persons = new LinkedList<>();

    //add to list
    public void attach(Person p) {
        persons.add(p);
    }

    public void detach(Person p) {
        persons.remove(p);
    }

    public void display(Action action) {
        for (Person p : persons) {
            p.accept(action);
        }
    }
}
