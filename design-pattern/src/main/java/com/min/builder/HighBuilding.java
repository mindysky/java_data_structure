package com.min.builder;

public class HighBuilding extends HouseBuilder{
    @Override
    public void buildBasic() {
        System.out.println("high building basic");
    }

    @Override
    public void buildWalls() {
        System.out.println("high building wall");
    }

    @Override
    public void buildRoof() {
        System.out.println("high building roof");
    }
}
