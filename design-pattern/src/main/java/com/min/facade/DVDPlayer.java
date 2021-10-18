package com.min.facade;

public class DVDPlayer {
    //Singleton
    private static final DVDPlayer  instance = new DVDPlayer();
    public static  DVDPlayer getInstance(){
        return instance;
    }

    public void on(){
        System.out.println("DVD on");
    }
    public void off(){
        System.out.println("DVD off");
    }
    public void play(){
        System.out.println("DVD play");
    }
    public void pause(){
        System.out.println("DVD pause");
    }
}
