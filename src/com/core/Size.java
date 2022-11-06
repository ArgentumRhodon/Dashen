package com.core;

/*
The size of any given graphic element of the game
Very handy for easilly determining placement, hit-boxes, offsets, etc.
 */
public class Size {

    private int width, height;

    public Size(int width, int height) {

        this.width = width;
        this.height = height;

    }

    public int getWidth() {

        return width;

    }

    public int getHeight() {

        return height;

    }

    public void add(Size size){

        this.width += size.getWidth();
        this.height += size.getHeight();

    }

    //debug
    public String toString(){

        return "[" + width + " x " + height + "]";

    }

}
