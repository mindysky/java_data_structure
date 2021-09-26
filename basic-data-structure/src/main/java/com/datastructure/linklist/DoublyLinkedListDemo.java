package com.datastructure.linklist;

public class DoublyLinkedListDemo {
    public static void main(String[] args) {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        HeroNode2 hero1 = new HeroNode2(1, "lucy", "lili");
        HeroNode2 hero2 = new HeroNode2(2, "david", "D");
        HeroNode2 hero3 = new HeroNode2(3, "Jack", "J");
        HeroNode2 hero4 = new HeroNode2(4, "Morgan", "M");

        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);

        doubleLinkedList.list();

        System.out.println("-----------------");
        HeroNode2 newHeroNode = new HeroNode2(2,"mimomi","MMMM");
        doubleLinkedList.update(newHeroNode);
        doubleLinkedList.list();

        System.out.println("-----------------");
        doubleLinkedList.delete(3);

        doubleLinkedList.list();

    }
}


//create double linked list
class DoubleLinkedList {
    private HeroNode2 head = new HeroNode2(0, "", "");

    public HeroNode2 getHead() {
        return head;
    }

    //添加一个节点到双向链表
    public void add(HeroNode2 heroNode) {
        HeroNode2 temp = head;
        //traverse the linked list
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        //while exit the while loop, temp point to the last one
        //形成双向链表
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    //update
    public void update(HeroNode2 newHeroNode) {
        if (head.next == null) {
            System.out.println("link list is empty---update");
            return;
        }
        HeroNode2 temp = head.next;
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
        if (head.next == null) {
            System.out.println("link list is empty,can not delete");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.pre.next = temp.next;
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("要删除的节点%dnot exist \n", no);
        }
    }

    //show linkedlist
    public void list() {
        if (head.next == null) {
            System.out.println("link is empty-----");
            return;
        }
        HeroNode2 temp = head.next;
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

class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next; //point to next node
    public HeroNode2 pre;// default null

    public HeroNode2(int no, String name, String nickname) {
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