package com.min.proxy.dynamicproxy;

import com.min.proxy.dynamicproxy.ITeacherDao;

public class TeacherDao implements ITeacherDao {
    @Override
    public void teach() {
        System.out.println("on class ====");
    }
}
