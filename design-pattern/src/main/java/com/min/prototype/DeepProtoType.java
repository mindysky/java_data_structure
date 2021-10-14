package com.min.prototype;

import java.io.*;

public class DeepProtoType implements Serializable, Cloneable {
    public String name;
    public DeepClonableTarget deepClonableTarget;

    public DeepProtoType() {
        super();
    }

    @Override
    public String toString() {
        return "DeepProtoType{" +
                "name='" + name + '\'' +
                ", deepClonableTarget=" + deepClonableTarget +
                '}';
    }

    //deep clone 1
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object deep = null;
        deep = super.clone();
        //address reference type separately
        DeepProtoType deepProtoType = (DeepProtoType) deep;
        deepProtoType.deepClonableTarget = (DeepClonableTarget) deepClonableTarget.clone();
        return deep;
    }

    //deep clone 2  serializable
    public Object deepClone(){
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;

        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);  //当前对象以对象流的方式输出

            //反序列化
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            return (DeepProtoType) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                bos.close();
                oos.close();
                bis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
