package com.hackrank.array;

import java.util.Scanner;

public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        CircleArray circleArray = new CircleArray(4);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(show):show queue");
            System.out.println("e(exit):exit queue");
            System.out.println("a(add):add queue");
            System.out.println("g(get):get queue");
            System.out.println("h(hear):show queue head");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    circleArray.showQueue();
                    break;
                case 'a':
                    System.out.println("please input a number");
                    int value = scanner.nextInt();
                    circleArray.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = circleArray.getQueue();
                        System.out.printf("get data %d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = circleArray.headQueue();
                        System.out.printf("the head of queue is %d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("app exit");
    }
}

class CircleArray {
    private int maxSize;
    private int front;  //head of queue
    private int rear;   //foot of queue
    private int[] arr; //simulate queue

    public CircleArray(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
    }

    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    public boolean isEmpty() {
        return rear == front;
    }

    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("queue is full -----");
            return;
        }
        arr[rear] = n;
        rear = (rear + 1) % maxSize;  //rear move on
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("queue is empty---");
        }
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    public void showQueue() {
        if (isEmpty()) {
            System.out.println("queue is empty---");
            return;
        }
        //traverse from front
        for (int i = front; i < front + getSize(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    //get valid size of the queue
    public int getSize() {
        return (rear + maxSize - front) % maxSize;
    }

    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("queue is empty---");
        }
        return arr[front];
    }

}