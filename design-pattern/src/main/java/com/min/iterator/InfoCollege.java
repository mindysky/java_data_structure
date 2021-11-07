package com.min.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfoCollege implements College{
    List<Department> departmentList= new ArrayList<>();

    public InfoCollege() {
        addDepartment("info1","info1");
        addDepartment("info2","info2");
        addDepartment("info3","info3");
        addDepartment("info4","info4");
    }

    @Override
    public String getName() {
        return "info college";
    }

    @Override
    public void addDepartment(String name, String desc) {
        Department department = new Department(name, desc);
        departmentList.add(department);
    }

    @Override
    public Iterator createIterotor() {
        return new InfoCollegeIterator(departmentList);
    }
}
