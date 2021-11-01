package com.min.proxy.staticproxy;

//static proxy object
public class TeacherProxy  implements ITeacherDao{
    private ITeacherDao target; //目标对象，通过接口聚合

    public TeacherProxy(ITeacherDao target) {
        this.target = target;
    }

    @Override
    public void teach() {
        System.out.println("proxy start....");
        target.teach();
        System.out.println("proxy stop....");
    }
}
