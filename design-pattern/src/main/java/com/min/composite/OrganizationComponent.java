package com.min.composite;

import com.sun.org.apache.bcel.internal.generic.FieldGenOrMethodGen;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.swing.undo.UndoableEditSupport;

public abstract class OrganizationComponent {
    private String name;
    private String des;

    public OrganizationComponent(String name, String des) {
        super();
        this.name = name;
        this.des = des;
    }

    protected void add(OrganizationComponent organizationComponent){
        //default
        throw new UnsupportedOperationException();
    };
    protected void remove(OrganizationComponent organizationComponent){
        throw new UnsupportedOperationException();
    };

    protected  abstract void print();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
