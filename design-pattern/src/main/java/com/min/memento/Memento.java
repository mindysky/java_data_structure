package com.min.memento;

public class Memento {
    private final String status;

    public Memento(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
