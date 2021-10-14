package com.min.builder;

public class CommonHouse extends HouseBuilder{
    @Override
    public void buildBasic() {
        System.out.println("common house basic");
    }

    @Override
    public void buildWalls() {
        System.out.println("common house wall");
    }

    @Override
    public void buildRoof() {
        System.out.println("common house roof");
    }
}
