package com.datastructure.linklist;

public class JosephuList {
    public static void main(String[] args) {

        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);

        circleSingleLinkedList.showBoy();


        circleSingleLinkedList.countBoy(1,2,5);


    }
}


//create circle single linked list
class CircleSingleLinkedList {
    //create first node
    private Boy first = new Boy(-1);

    //add boy node
    public void addBoy(int nums) {
        if (nums < 1) {
            System.out.println("nums is wrong");
            return;
        }
        Boy curBoy = null;
        for (int i = 1; i <= nums; i++) {
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                first.setNext(first); //构成环
                curBoy = first;
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    public void showBoy() {
        if (first == null) {
            System.out.println("no boys");
            return;
        }

        Boy curBoy = first;
        while (true) {
            System.out.printf("boy no %d\n", curBoy.getNo());
            if (curBoy.getNext() == first) {
                break;
            }
            curBoy = curBoy.getNext();
        }
    }

    //根据用户输入，计算出小孩出圈顺序

    /**
     *
     * @param startNo    start from
     * @param countNum   count num
     * @param nums   total num
     */
    public void countBoy(int startNo, int countNum, int nums) {
        if(first==null || startNo<1 || startNo>nums){
            System.out.println("params is wrong, pls try again");
            return;
        }
        Boy helper = first;
        while (true){
            if(helper.getNext()==first){
                break;
            }
            helper = helper.getNext();
        }
        for (int j = 0; j < startNo-1; j++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        //
        while (true){
            if(helper==first){
                break;
            }
            //
            for (int j = 0; j < countNum-1; j++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            System.out.printf("boy no out %d\n",first.getNo());
            first = first.getNext();
            helper.setNext(first);
        }
        System.out.println("最后留在圈中的编号"+ first.getNo());
    }


}


class Boy {
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}