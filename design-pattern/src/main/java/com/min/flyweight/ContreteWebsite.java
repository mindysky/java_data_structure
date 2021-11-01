package com.min.flyweight;

public class ContreteWebsite extends WebSite {
    private String type = "";

    public ContreteWebsite(String type){
        this.type = type;
    }

    @Override
    public void use(User user) {
        System.out.println(user.getName()+ " publish type === " + type);
    }
}
