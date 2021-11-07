package com.min.memento;

import com.min.mediator.Mediator;

import javax.swing.*;

public class Originator {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //create  a method to save the status
    public Memento saveMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getStatus();
    }
}
