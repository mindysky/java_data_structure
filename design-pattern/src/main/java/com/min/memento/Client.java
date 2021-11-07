package com.min.memento;

public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker();

        originator.setState("state 01");
        careTaker.add(originator.saveMemento());


        originator.setState("state 02");
        careTaker.add(originator.saveMemento());

        originator.setState("state 03");
        careTaker.add(originator.saveMemento());

        System.out.println(originator.getState());
        //back to state1
        originator.getStateFromMemento(careTaker.get(0));

        System.out.println(originator.getState());
    }
}
