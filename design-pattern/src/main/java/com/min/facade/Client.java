package com.min.facade;

public class Client {
    public static void main(String[] args) {
        HomeTheatreFacade homeTheatreFacade = new HomeTheatreFacade();
        homeTheatreFacade.ready();
        System.out.println("+++++++++");
        homeTheatreFacade.play();
        System.out.println("============");
        homeTheatreFacade.end();
    }
}
