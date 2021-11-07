package com.min.observer;

import java.util.ArrayList;

public class WeatherData implements Subject {
    private float temperature;
    private float pressure;
    private float humidity;

    private final ArrayList<Observer> observers;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    public void dataChange() {
        notifyObservers();
    }

    public void setData(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;

        dataChange();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(this.temperature,this.pressure,this.humidity);
        }

    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}
