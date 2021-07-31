package com.hackrank.thread;

import java.awt.*;

public class WindowTest1 {
    public static void main(String[] args) {

        Window1 w = new Window1();

        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);

        t1.setName("window1");
        t2.setName("window2");
        t3.setName("window3");


        t1.start();
        t2.start();
        t3.start();

    }
}

class Window1 implements Runnable {
    private int ticket = 100;
    private Object obj = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (obj){
                if (ticket > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "ticket=====" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }

        }
    }
}
