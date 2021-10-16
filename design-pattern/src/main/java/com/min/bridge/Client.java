package com.min.bridge;

public class Client {
    public static void main(String[] args) {
        FoldedPhone foldedPhone = new FoldedPhone(new Vivo());
        foldedPhone.open();
        foldedPhone.call();
        foldedPhone.close();
    }
}
