package com.min.observer;

public class Client {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentCondition currentCondition = new CurrentCondition();

        weatherData.registerObserver(currentCondition);

        System.out.println("notify observers");

        weatherData.setData(10f,100f,39f);
    }
}
