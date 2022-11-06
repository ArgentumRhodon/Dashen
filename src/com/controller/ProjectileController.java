package com.controller;

import com.entity.MovingEntity;
//a projectile controller that handles whether the projectile goes left or right
public class ProjectileController implements EntityController{
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;

    public ProjectileController() {
    }

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

    public void setMotion(MovingEntity caster){

        switch (caster.getDirection()){

            case E -> right = true;

            case W -> left = true;

        }

    }

//    public void setMotion(Position current, Position mouse){
//
//        Vector2D direction = Vector2D.directionBetweenPositions(current, mouse);
//
//    }

}
