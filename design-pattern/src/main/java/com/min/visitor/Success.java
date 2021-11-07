package com.min.visitor;

public class Success extends Action{
    @Override
    public void getManResult(Man man) {
        System.out.println("man comment success");
    }

    @Override
    public void getWomanResult(Woman woman) {
        System.out.println("woman comment success");
    }
}
