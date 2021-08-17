package com.hackrank.reflection;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    //use reflection
    @Test
    public void test2() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<Person> claz = Person.class;
        Constructor cons = claz.getConstructor(String.class,int.class);

        Object obj = cons.newInstance("tom",12);
        Person p = (Person) obj;

        System.out.println(p.toString());

        Field age = claz.getDeclaredField("age");
        age.set(p,10);
        System.out.println(p.toString());

        Method show = claz.getDeclaredMethod("show");
        show.invoke(p);

        //通过反射，可以调用Person类的私有结构，比如： 私有的构造器，方法，属性
        Constructor cons1 = claz.getDeclaredConstructor(String.class);
        cons1.setAccessible(true);
        Person p1 = (Person) cons1.newInstance("Jerry");
        System.out.println(p1);

    }

    //获取Class实例的方式
    @Test
    public void getClasses() throws ClassNotFoundException {
        //方式1:
        Class clazz1 = Person.class;
        System.out.println(clazz1);

        //方式2
        Person p1 = new Person();
        Class clazz2 = p1.getClass();
        System.out.println(clazz2);

        //方式3: 用得最多
        Class clazz3 = Class.forName("com.hackrank.reflection.Person");
        System.out.println(clazz3);

        System.out.println(clazz1==clazz3);

        //方式4： 使用类的加载器
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        Class clazz4 = classLoader.loadClass("com.hackrank.reflection.Person");
        System.out.println(clazz4);
    }
}
