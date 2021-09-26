package com.datastructure.hashtable;

import java.util.Scanner;

public class HashTableDemo {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(7);

        //brief menu
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add: add employee");
            System.out.println("show: show employee");
            System.out.println("find: input find id for employee");
            System.out.println("exit: exit");

            key = scanner.next();

            switch (key) {
                case "add":
                    System.out.println("input id");
                    int id = scanner.nextInt();
                    System.out.println("input name");
                    String name = scanner.next();
                    //create emp
                    Emp emp = new Emp(id, name);
                    hashTable.add(emp);
                    break;
                case "show":
                    hashTable.list();
                    break;
                case "find":
                    System.out.println("input find id");
                    id = scanner.nextInt();
                    hashTable.findEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }

        }

    }
}


//create hash table
class HashTable {
    private EmpLinkedList[] empLinkedLists;
    private int size; //表示共有多少条链表

    public HashTable(int size) {
        this.size = size;
        //init emplinkedList
        empLinkedLists = new EmpLinkedList[size];
        //init link
        for (int i = 0; i < size; i++) {
            empLinkedLists[i] = new EmpLinkedList();
        }

    }

    public void add(Emp emp) {
        //根据员工ID，得到该员工应当添加到哪条链表
        int empLinkedListNO = hashFun(emp.id);
        empLinkedLists[empLinkedListNO].add(emp);
    }

    //遍历所有的链表，遍历hashtable
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkedLists[i].list(i);
        }
    }

    public void findEmpById(int id) {
        int empLinkedListNO = hashFun(id);
        Emp emp = empLinkedLists[empLinkedListNO].findEmpById(id);

        if (emp != null) {//找到
            System.out.printf("find %d id = %d \n",empLinkedListNO+1,id);
        }else{
            System.out.println("not found");
        }

    }

    //编写散列函数，使用一个简单取模法
    public int hashFun(int id) {
        return id % size;
    }
}


//表示雇员
class Emp {
    public int id;
    public String name;
    public Emp next;  //next默然为null

    public Emp(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}


//创建empLinkedList
class EmpLinkedList {
    //头指针，执行第一个Emp， 因此我们这个链表的head,是直接指向第一个Emp

    private Emp head; //默认null
    //添加雇员到链表

    /*
     * 1.假定， 添加雇员时，ID是自增的，即ID的分配总是从小到大
     * 因此我们将该雇员直接加入到本链表最后即可
     * */

    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
        //如果不是第一个雇员，则使用一个辅助指针，帮助定位到最后
        Emp curEmp = head;

        while (true) {
            if (curEmp.next == null) {
                //说明到链表最后
                break;
            }
            curEmp = curEmp.next; //后移
        }

        //退出时直接将emp加入链表
        curEmp.next = emp;

    }


    //遍历链表的雇员信息
    public void list(int no) {
        if (head == null) {
            System.out.println("link list " + (no + 1) + " is empty");
            return;
        }
        System.out.println((no + 1) + " the information of current LinkedLis is ");
        Emp curEmp = head; //辅助指针
        while (true) {
            System.out.printf("=》 id=%d  name = %s\t\n", curEmp.id, curEmp.name);
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next; //后移
        }

    }

    //find emp by id
    public Emp findEmpById(int id) {
        if (head == null) {
            System.out.println("Find-----list is empty");
            return null;
        }
        Emp curEmp = head;
        while (true) {
            if (curEmp.id == id) {
                break; //此时curEmp指向要查找的雇员
            }
            if (curEmp.next == null) { //说明遍历当前链表没有找到该雇员
                curEmp = null;
                break;
            }
            curEmp = curEmp.next; //rearward
        }
        return curEmp;
    }

}