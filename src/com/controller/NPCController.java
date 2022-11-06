package com.controller;

import com.core.Position;
//a movement controller for NPCs
public class NPCController implements EntityController {

    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;

    @Override
    public boolean isRequestingUp() {
        return up;
    }

    @Override
    public boolean isRequestingRight() {
        return right;
    }

    @Override
    public boolean isRequestingDown() {
        return down;
    }

    @Override
    public boolean isRequestingLeft() {
        return left;
    }

    @Override
    public boolean isRequestingCastSpell() {
        return false;
    }

    /*
    Takes in two positions and returns the appropriate movement commands
    to get from the current position to the start position.
     */
    public void moveToTarget(Position target, Position current) {

        double deltaX = target.getX() - current.getX();
        double deltaY = target.getY() - current.getY();

        up = deltaY < 0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        right = deltaX > 0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;
        down = deltaY > 0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        left = deltaX < 0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;

    }

    //stops movement in all directions
    public void stop() {

        up = false;
        right = false;
        down = false;
        left = false;

    }

}
