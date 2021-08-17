package com.hackrank.reflection3;

import com.hackrank.reflection2.Person;
import org.junit.Test;

import java.lang.reflect.Method;

public class MethodTest {
    @Test
    public void test1(){
        Class<Person> personClass = Person.class;

        Method[] methods = personClass.getMethods();
        for(Method m:methods){
            System.out.println(m);
        }

        System.out.println();

        Method[] declaredMethods = personClass.getDeclaredMethods();
        for(Method m:declaredMethods){
            System.out.println(m);
        }
    }
}
