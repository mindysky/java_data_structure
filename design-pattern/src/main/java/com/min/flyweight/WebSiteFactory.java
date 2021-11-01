package com.min.flyweight;

import java.util.HashMap;

public class WebSiteFactory {
    private HashMap<String,ContreteWebsite> pool = new HashMap<>();

    //根据网站的类型，返回一个网站，如果没有就创建一个网站，并放入池中，并返回
    public WebSite getWebSiteCategory(String type){
        if(!pool.containsKey(type)){
            pool.put(type,new ContreteWebsite(type));
        }
        return (WebSite)pool.get(type);
    }

    public int getWebSiteCount(){
        return pool.size();
    }
}
