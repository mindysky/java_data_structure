package com.hackrank.reflection;

import org.junit.Test;

import java.util.Random;

public class NewInstanceTest {
    /*
     * 通过反射创建运行时类的对象
     * */

    @Test
    public void test1() throws InstantiationException, IllegalAccessException {
        Class<Person> clazz = Person.class;

        /*
         * newInstance(): 调用此方法，创建对应的运行时类的对象\
         * 内部是调用了运行时类的空参的构造器
         * 要想此方法正常创建运行时类的对象，要求
         * 1. 运行时类必须提供空参构造器
         * 2. 空参的构造器的访问权限得够，通常设置为public
         * 在java bean中要求提供一个public的空参构造器 原因：
         * 1. 便于通过反射，创建运行时类的对象
         * 2. 便于子类继承此运行时类时，默认调用super()方法时，保证父类有次构造器
         * */
        Person p1 = clazz.newInstance();
        System.out.println(p1);

    }

    @Test
    public void test2() {
        int num = new Random().nextInt(3);
        String classPath= "";
        switch (num){
            case 0:
                classPath = "java.util.Date";
                break;
            case 1:
                classPath = "java.lang.Object";
                break;
            case 2:
                classPath ="com.hackrank.reflection.Person";
                break;
            default:
                break;
        }


        try {
            Object instance = getInstance(classPath);
            System.out.println(instance);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    public Object getInstance(String classPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(classPath);
        return clazz.newInstance();
    }

    @Test
    public void test3(){

    }

}
