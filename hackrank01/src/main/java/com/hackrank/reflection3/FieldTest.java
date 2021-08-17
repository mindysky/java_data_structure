package com.hackrank.reflection3;

import com.hackrank.reflection2.Person;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/*
* 获取当前运行时类的属性结构
* */
public class FieldTest {

    @Test
    public void test1(){
        Class clazz = Person.class;
        //获取属性结构
        //getFields(): 获取当前运行时类及其父类中声明为public访问权限的属性
        Field[] fields = clazz.getFields();
        for(Field f:fields){
            System.out.println(f);
        }

        System.out.println();
        //getDeclaredFields(): 获取当前运行时类中声明的所有属性（不包含父类声明的属性）
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field field:declaredFields){
            System.out.println("declare----"+field);
        }
    }

    //权限修饰符 数据类型  变量名
    @Test
    public void test2(){
        Class clazz = Person.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field field:declaredFields){
            //权限修饰符
            int modifiers = field.getModifiers();
            System.out.println(Modifier.toString(modifiers));

            System.out.println("================");
            //数据类型
            Class<?> type = field.getType();
            System.out.println(type);

            System.out.println("=============");
            //变量名
            String name = field.getName();
            System.out.println(name);

            System.out.println("---------------");
        }
    }
}
