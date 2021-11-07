package com.min.iterator;

import com.min.command.Command;

import java.util.Iterator;

public class ComputerCollege implements College {
    Department[] departments= new Department[5];
    int numOfDepartment = 0;

    public ComputerCollege(){
        addDepartment("computer1","computer1");
        addDepartment("computer2","computer2");
        addDepartment("computer3","computer3");
        addDepartment("computer4","computer4");
        addDepartment("computer5","computer5");
    }

    @Override
    public String getName() {
        return "Computer College";
    }

    @Override
    public void addDepartment(String name, String desc) {
        Department department = new Department(name, desc);
        departments[numOfDepartment] = department;
        numOfDepartment += 1;

    }

    @Override
    public Iterator createIterotor() {
        return new ComputerCollegeIterator(departments);
    }
}
