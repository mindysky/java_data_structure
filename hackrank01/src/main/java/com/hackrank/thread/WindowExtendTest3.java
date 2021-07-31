package com.hackrank.thread;
/*
 * 使用同步方法处理继承Thread类的线程安全问题
 *
 * */

public class WindowExtendTest3 {
    public static void main(String[] args) {

        Window3 t1 = new Window3();
        Window3 t2 = new Window3();
        Window3 t3 = new Window3();


        t1.setName("window1");
        t2.setName("window2");
        t3.setName("window3");


        t1.start();
        t2.start();
        t3.start();

    }
}

class Window3 extends Thread {
    private static int ticket = 100;
    @Override
    public void run() {
        while (true) {
            show();


        }
    }

    private static synchronized void show() {
        if (ticket > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "ticket=====" + ticket);
            ticket--;
        }

    }
}
