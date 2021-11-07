package com.min.visitor;

public class Fail extends Action{
    @Override
    public void getManResult(Man man) {
        System.out.println("man comment fail");
    }

    @Override
    public void getWomanResult(Woman woman) {
        System.out.println("woman comment fail");
    }
}
