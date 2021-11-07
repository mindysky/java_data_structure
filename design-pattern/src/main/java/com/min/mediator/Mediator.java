package com.min.mediator;

public abstract class Mediator {
    public abstract void Register(String colleagueName, Colleague colleague);
    public abstract void GetMessage(int stateChange,String colleague);

    public abstract void sendMessage();
}
