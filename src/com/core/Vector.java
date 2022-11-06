package com.core;

/*
This class is the representation of each gameObject's direction.
 */

public class Vector {

    //returns a vector given a direction
    public static Vector vectorFromDirection(Direction direction){

        return switch (direction) {
            case E -> new Vector(1, 0);
            case W -> new Vector(-1, 0);
        };

    }

    //implemented but unused feature for NPCs targeting player
    public static double dotProduct(Vector vector1, Vector vector2){

        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY();

    }

    //returns the direction between two different positions
    public static Vector directionBetweenPositions(Position start, Position end){

        Vector direction = new Vector(start.getX() - end.getX(), start.getY() - end.getY());
        direction.normalize();
        return direction;

    }

    private double x, y;

    public Vector(double x, double y) {

        this.x = x;
        this.y = y;

    }
    //Pythagoras' theorem used to get length of vector

    public double length(){

        return Math.sqrt(x * x + y * y);

    }

    //normalizing the vector
    public void normalize(){

        double length = length();

        x = x == 0 ? 0 : x / length;
        y = y == 0 ? 0 : y / length;

    }

    public void multiply(double speed) {

        x *= speed;
        y *= speed;

    }

    public void add(Vector vector) {

        x += vector.getX();
        y += vector.getY();

    }

    public double getX() {

        return x;

    }

    public double getY() {

        return y;

    }

}
