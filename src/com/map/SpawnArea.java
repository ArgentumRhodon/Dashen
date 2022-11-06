package com.map;

import java.awt.*;
//A simple class to allow spawn areas in Map to be more easily managed.
public class SpawnArea extends Rectangle {

    public SpawnArea(int x, int y, int width, int height) {
        super(x, y, width, height);


    }

    public int getArea(){

        return width * height;

    }

}
