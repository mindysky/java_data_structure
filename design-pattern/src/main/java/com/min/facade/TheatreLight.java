package com.min.facade;

public class TheatreLight {
    private static final TheatreLight instance = new TheatreLight();

    public static TheatreLight getInstance() {
        return instance;
    }

    public void on() {
        System.out.println("Stereo on");
    }

    public void off() {
        System.out.println("Stereo off");
    }

    public void dim() {
        System.out.println("Stereo dim");
    }

    public void bright() {
        System.out.println("Stereo bright");
    }

}
