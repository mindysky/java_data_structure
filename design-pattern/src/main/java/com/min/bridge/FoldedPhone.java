package com.min.bridge;

public class FoldedPhone extends Phone{
    public FoldedPhone(Brand brand) {
        super(brand);
    }
    public void open(){
        super.open();
        System.out.println("FoldedPhone");
    }
    public void call(){
        super.call();
        System.out.println("FoldedPhone");
    }
    public void close(){
        super.close();
        System.out.println("FoldedPhone");
    }
}
