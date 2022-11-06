package com.core;

/*
An enum representing a gameObjects direction/way-facing
 */
public enum Direction {
    E(0),
    W(1);

    private final int animationRow;

    //in the sprite sheets, row 0 (top) is east and row 1 (bottom) is west
    Direction(int animationRow) {

        this.animationRow = animationRow;

    }

    //since dealing with only east and west, only the x factor matters
    public static   Direction fromMotion(Movement movement, Direction direction){

        double x = movement.getVector().getX();

        if(x < 0) return W;
        if(x > 0) return E;

        return direction;

    }

    public int getAnimationRow() {

        return animationRow;

    }

}
