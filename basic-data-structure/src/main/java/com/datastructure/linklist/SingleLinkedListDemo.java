package com.datastructure.linklist;

import java.util.Stack;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1, "lucy", "lili");
        HeroNode hero2 = new HeroNode(2, "david", "D");
        HeroNode hero3 = new HeroNode(3, "Jack", "J");
        HeroNode hero4 = new HeroNode(4, "Morgan", "M");

        //create singleLinked list
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero4);


        singleLinkedList.list();
        HeroNode newHeroNode = new HeroNode(2, "小王", "王");

        //update
        singleLinkedList.update(newHeroNode);
        System.out.println("-------------");
        singleLinkedList.list();

        //delete
        System.out.println("------------del");
        singleLinkedList.delete(1);

        singleLinkedList.list();


        System.out.println("valid link length: ------" + getLength(singleLinkedList.getHead()));


//        HeroNode res = getLastK(singleLinkedList.getHead(), 3);
//
//        System.out.println(res);
        singleLinkedList.list();
        System.out.println("------------reverseLink");

        reverseList(singleLinkedList.getHead());

        singleLinkedList.list();

        System.out.println("------------reversePrint-----------");
        reversePrintList(singleLinkedList.getHead());



    }


    //reverse print
    public static void  reversePrintList(HeroNode head){
        if(head.next==null){
            return;
        }
        Stack<HeroNode> stack = new Stack<HeroNode>();
        HeroNode cur = head.next;
        while (cur!=null){
            stack.push(cur);
            cur = cur.next;  //让cur后移
        }

        while (stack.size()>0){
            System.out.println(stack.pop());
        }

    }

    //reverse singleLinkedList
    public static void reverseList(HeroNode head){
        if(head.next==null||head.next.next==null){
            return;
        }
        //create a temp
        HeroNode cur = head.next;
        HeroNode next = null;  //指向当前节点的下一个节点
        HeroNode reverseHead = new HeroNode(0,"","");

        while (cur!=null){
            next = cur.next;  //保存当前节点的下一个节点
            cur.next = reverseHead.next;//将cur的下一个节点指向新的链表最前端
            reverseHead.next = cur; //将cur连接到新的链表上
            cur = next;  //让cur后移
        }
        //将head.next指向reverseHead.next;
        head.next = reverseHead.next;
    }

    //find the K from the last
    public static HeroNode getLastK(HeroNode head, int k) {
        if (head.next == null) {
            return null;
        }
        int size = getLength(head);
        if (k <= 0 || k > size) {
            return null;
        }
        // for循环定位倒数index
        HeroNode cur = head.next;
        for (int i = 0; i < size - k; i++) {
            cur = cur.next;
        }
        return cur;
    }

    //get length of the link node
    public static int getLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        int length = 0;

        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }
}

//define a single linked list to manage the heros
class SingleLinkedList {
    private HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return head;
    }

    public void add(HeroNode heroNode) {
        HeroNode temp = head;
        //traverse the linked list
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        //while exit the while loop, temp point to the last one
        temp.next = heroNode;
    }


    public void addByOrder(HeroNode heroNode) {
        //coz head can not move, we need a auxiliary point
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            //temp in the end
            if (temp.next == null) {
                break;
            }
            //add after temp
            if (temp.next.no > heroNode.no) {
                break;
            } else if (temp.next.no == heroNode.no) {

                flag = true; //no exist
                break;
            }
            temp = temp.next; //rearward
        }
        //get flag value
        if (flag) {
            System.out.printf("no : %d exist, can not add ", heroNode.no);
        } else {
            heroNode.next = temp.next;
            temp.next = heroNode;
        }


    }


    //update node info depend on NO
    public void update(HeroNode newHeroNode) {
        if (head.next == null) {
            System.out.println("link list is empty---update");
            return;
        }
        HeroNode temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == newHeroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else {
            System.out.printf("can not find no %d\n", newHeroNode.no);
        }
    }

    //delete node
    public void delete(int no) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.next = temp.next.next;
        }
    }

    //show linkedlist
    public void list() {
        if (head.next == null) {
            System.out.println("link is empty-----");
            return;
        }
        HeroNode temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            //move temp to next position
            temp = temp.next;
        }

    }

}

class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next; //point to next node

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}