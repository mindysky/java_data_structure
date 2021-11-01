package com.min.proxy.dynamicproxy;

public class Client {
    public static void main(String[] args) {
        ITeacherDao teacherDao = (ITeacherDao) new TeacherDao();

        ITeacherDao proxyInstance = (ITeacherDao) new ProxyFactory(teacherDao).getProxyInstance();
        proxyInstance.teach();

    }
}
