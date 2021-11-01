package com.min.proxy.staticproxy;

public class Client {
    public static void main(String[] args) {
        TeacherDao teacherDao = new TeacherDao();

        TeacherProxy teacherProxy = new TeacherProxy(teacherDao);
        teacherProxy.teach();
    }
}
