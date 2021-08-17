package com.hackrank.reflection2;

@MyAnnotation(value = "hi")
public class Person extends Creature<String> implements Comparable<String>,MyInterFace{
    private String name;
    int age;
    public int id;

    @MyAnnotation(value = "abc")
    private Person(String name) {
        this.name = name;
    }

    public Person(){}

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String display(String interest){
        return interest;
    }

    @MyAnnotation
    private String show(String nation){
        System.out.println("nation======="+nation);
        return nation;
    }

    @Override
    public void info() {
        System.out.println("person~");
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }


}
