package com.min.observer;

import java.awt.*;
import java.util.ArrayList;

public class CurrentCondition implements Observer {
    private float temperature;
    private float pressure;
    private float humidity;

    public void update(float temperature,float pressure,float humidity){
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }

    public void display(){
        System.out.println("temperature==="+ temperature);
        System.out.println("pressure==="+ pressure);
        System.out.println("humidity==="+ humidity);
    }

}
