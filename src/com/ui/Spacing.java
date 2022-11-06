package com.ui;

import javax.swing.*;

//for internal and external spacing of UI
public class Spacing {

    private final int top;
    private final int right;
    private final int bottom;
    private final int left;

    public Spacing(int horizontal, int vertical) {
        this(vertical, horizontal, vertical, horizontal);

    }

    public Spacing(int top, int right, int bottom, int left) {

        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;

    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public Spacing(int spacing) {

        this(spacing, spacing);

    }

    public int getVertical(){

        return top + bottom;

    }

    public int getHorizontal(){

        return left + right;

    }

}
