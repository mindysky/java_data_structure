package com.min.builder;

import javax.lang.model.element.VariableElement;

public abstract class HouseBuilder {
    protected House house = new House();

    public abstract void buildBasic();
    public abstract void buildWalls();
    public abstract void buildRoof();
    public House buildHouse(){
        return house;
    }
}
