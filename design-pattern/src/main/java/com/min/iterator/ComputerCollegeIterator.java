package com.min.iterator;

import java.util.Iterator;

public class ComputerCollegeIterator implements Iterator {
    //需要知道department是以怎样的方式存放的
    Department[] departments;
    int position = 0;

    public ComputerCollegeIterator(Department[] departments) {
        this.departments = departments;
    }

    @Override
    public boolean hasNext() {
        if (position >= departments.length || departments[position] == null) {
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Object next() {
        Department department = departments[position];
        position+=1;
        return department;
    }

    public void remove(){

    }
}
