package com.min.proxy.cglib;

import com.min.proxy.staticproxy.TeacherDao;

public class Client {
    public static void main(String[] args) {
        TeacherDao teacherDao = new TeacherDao();
        TeacherDao proxyFactory = (TeacherDao) new ProxyFactory(teacherDao).getProxyInstance();
        proxyFactory.teach();
    }
}
