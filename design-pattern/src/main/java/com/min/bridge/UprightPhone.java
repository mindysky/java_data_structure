package com.min.bridge;

public class UprightPhone extends Phone{
    public UprightPhone(Brand brand) {
        super(brand);
    }
    public void open(){
        super.open();
        System.out.println("upright phone");
    }
    public void call(){
        super.call();
        System.out.println("upright phone");
    }
    public void close(){
        super.close();
        System.out.println("upright phone");
    }
}
