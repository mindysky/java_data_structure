package com.hackrank.array;

import java.util.Scanner;

public class ArrayQueue {
    public static void main(String[] args) {
        ArrayQueueNew arrayQueueNew= new ArrayQueueNew(3);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop){
            System.out.println("s(show):show queue");
            System.out.println("e(exit):exit queue");
            System.out.println("a(add):add queue");
            System.out.println("g(get):get queue");
            System.out.println("h(hear):show queue head");
            key = scanner.next().charAt(0);
            switch (key){
                case 's':
                    arrayQueueNew.showQueue();
                    break;
                case 'a':
                    System.out.println("please input a number");
                    int value = scanner.nextInt();
                    arrayQueueNew.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueueNew.getQueue();
                        System.out.printf("get data %d\n",res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                   try {
                       int res = arrayQueueNew.headQueue();
                       System.out.printf("the head of queue is %d\n",res);
                   }catch (Exception e){
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


class ArrayQueueNew {
    private int maxSize;
    private int front;  //head of queue
    private int rear;   //foot of queue
    private int[] arr; //simulate queue

    //create queue constructor
    public ArrayQueueNew(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = -1; //point the position before the begin of the queue
        rear = -1; //point the position after the end of queue
    }

    //Determine whether the queue is full
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    //determine whether the queue is empty
    public boolean isEmpty() {
        return rear == front;
    }

    //add data to queue
    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("queue is full---------");
            return;
        }
        rear++;
        arr[rear] = n;
    }

    //get data from queue
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("queue is empty, no data------------");
        }
        front++;
        return arr[front];
    }

    //show all element in queue
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("queue is empty");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    //show begin of the queue
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("no data");
        }
        return arr[front + 1];
    }
}
