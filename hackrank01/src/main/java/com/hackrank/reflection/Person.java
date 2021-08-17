package com.hackrank.reflection;

public class Person {
    private String name;
    public int age;

    public void setName(String name) {
        this.name = name;
    }


    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Person(String name){
        this.name = name;
    }

    public void show(){
        System.out.println("hello hello");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

}
