package com.core;

/*
Position handles the positions of all gameObjects and UI
Position helps allow positions of each element to be more
    easily controlled/handled
 */

public class Position {

    //An allowance value for considering one position close to another
    public static final int PROXIMITY_RANGE = 64;

    //returns a copy of a given position instead of a reference to said position
    public static Position copyOf(Position position) {

        return new Position(position.getX(), position.getY());

    }

    //x and y coordinates of position
    private double x, y;

    public Position(double x, double y) {

        this.x = x;
        this.y = y;

    }

    public Position(int x, int y) {

        this.x = x;
        this.y = y;

    }

    //some libraries, as you likely know, use int input, so this is just a convenient cast
    public int intX(){

        return (int) Math.round(x);

    }

    //some libraries, as you likely know, use int input, so this is just a convenient cast
    public int intY(){

        return (int) Math.round(y);

    }

    public double getX() {

        return x;

    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {

        return y;

    }

    //applies a given motion to the position
    public void apply(Movement movement) {

        applyX(movement);
        applyY(movement);

    }

    //returns true if the current position is close to the given position
    public boolean isInRangeOf(Position position) {

        return Math.abs(x - position.getX()) < Position.PROXIMITY_RANGE && Math.abs(y - position.getY()) < Position.PROXIMITY_RANGE;

    }

    //applies a motion to only the x coordinate
    public void applyX(Movement movement) {

        x += movement.getVector().getX();

    }

    //applies a motion to only the y coordinate
    public void applyY(Movement movement) {

        y += movement.getVector().getY();

    }

    //adds a position to this position
    public void add(Position position) {

        x += position.getX();
        y += position.getY();

    }

    //subtracts a position from this position
    public void subtract(Position position){

        x -= position.getX();
        y -= position.getY();

    }

    //determines the distance between this position and another
    public double distanceTo(Position other){

        double deltaX = this.getX() - other.getX();
        double deltaY = this.getY() - other.getY();

        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

    }

}
