package com.min.prototype;

import java.io.Serializable;

public class DeepClonableTarget implements Serializable, Cloneable {
    private String cloneName;
    private String cloneClass;

    public DeepClonableTarget(String cloneName, String cloneClass) {
        this.cloneName = cloneName;
        this.cloneClass = cloneClass;
    }

    @Override
    public String toString() {
        return "DeepClonableTarget{" +
                "cloneName='" + cloneName + '\'' +
                ", cloneClass='" + cloneClass + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
