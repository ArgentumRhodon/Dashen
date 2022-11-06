package com.core;

import com.controller.EntityController;

/*
Handles gameObject movement. This class holds a normalized
    direction vector and a speed.
 */

public class Movement {

    //represents the direction in which the object is moving
    private Vector vector;
    //the speed at which said object in moving in said direction
    private double speed;

    public Movement(double speed) {

        this.speed = speed;
        this.vector = new Vector(0,0);

    }

    /*
    Takes information from the controller and applies the appropriate
    changes in x and y depending on input represented from said controller.
     */
    public void update(EntityController entityController){

        int deltaX = 0, deltaY = 0;

        if(entityController.isRequestingUp()){

            deltaY--;

        }

        if(entityController.isRequestingDown()){

            deltaY++;

        }

        if(entityController.isRequestingLeft()){

            deltaX--;

        }

        if(entityController.isRequestingRight()){

            deltaX++;

        }

        vector = new Vector(deltaX, deltaY);
        vector.normalize();
        vector.multiply(speed);

    }

    public Vector getVector() {

        return vector;

    }

    //if the character is moving, the absolute length of the vector is > 0
    public boolean isMoving() {

        return vector.length() > 0;

    }

    //stops the character in either direction based on input
    public void stop(boolean stopX, boolean stopY) {

        vector = new Vector(
                stopX ? 0 : vector.getX(),
                stopY ? 0 : vector.getY());

    }

    //adds one vector to this class's instance vector
    public void add(Vector vector){

        this.vector.add(vector);

    }

    public void setSpeed(double speed) {

        this.speed = speed;

    }

}
