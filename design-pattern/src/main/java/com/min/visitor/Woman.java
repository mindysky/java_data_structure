package com.min.visitor;

/**
 * 1. 这里我们使用到了双分派，即首先在客户端程序中，将具体状态作为参数传递woman中（第一次分派）
 * 2. 然后woman类吊桶作为参数的具体方法中的getWomanResult, 同时将自己（this）作为参数传入，完成第二次分派
 */
public class Woman extends Person {
    @Override
    public void accept(Action action) {
        action.getWomanResult(this);
    }
}
