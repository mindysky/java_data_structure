package com.min.iterator;

import com.min.facade.Screen;
import com.sun.deploy.panel.ITreeNode;

import java.util.Iterator;
import java.util.List;

public class OutputImpl {
    List<College> colleges;

    public OutputImpl(List<College> colleges){
        this.colleges = colleges;
    }

    public void printCollege(){
        Iterator<College> iterator = colleges.iterator();
        while (iterator.hasNext()){
            College next = iterator.next();
            System.out.println(next.getName());
            printDepartment(next.createIterotor());
        }

    }

    public void printDepartment(Iterator iterator){
        while (iterator.hasNext()){
            Department d = (Department) iterator.next();
            System.out.println(d.getName());
        }
    }
}
