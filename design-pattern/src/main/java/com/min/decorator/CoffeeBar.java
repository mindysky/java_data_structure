package com.min.decorator;

public class CoffeeBar {
    public static void main(String[] args) {
        Drink longBlack = new LongBlack();
        System.out.println(longBlack.cost());

        longBlack = new Milk(longBlack);
        longBlack = new Chocolate(longBlack);
        System.out.println(longBlack.cost());
        System.out.println(longBlack.getDes());
    }
}
