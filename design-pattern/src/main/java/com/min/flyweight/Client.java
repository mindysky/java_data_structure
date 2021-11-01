package com.min.flyweight;

public class Client {
    public static void main(String[] args) {
        WebSiteFactory webSiteFactory = new WebSiteFactory();
        WebSite news = webSiteFactory.getWebSiteCategory("news");
        news.use(new User("xiao wang"));
        WebSite blog = webSiteFactory.getWebSiteCategory("blog");
        blog.use(new User("xiao li"));

        System.out.println(webSiteFactory.getWebSiteCount());
    }
}
