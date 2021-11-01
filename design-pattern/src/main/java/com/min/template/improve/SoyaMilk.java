package com.min.template.improve;

public abstract class SoyaMilk {
    final void make(){
        select();
        if(customWantCondiments()){
            addCondiments();
        }
        soak();
        beat();
    };
    void select(){
        System.out.println("#1 select beans");
    };
    abstract void addCondiments();
    void soak(){
        System.out.println("#3 soak");
    };
    void beat(){
        System.out.println("#4 beat ");
    };

    boolean customWantCondiments(){
        return true;
    }
}
