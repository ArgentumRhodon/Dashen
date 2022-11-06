package com.entity;

import com.controller.EntityController;
import com.core.*;
import com.state.State;
import com.gfx.AnimationManager;
import com.gfx.SpriteLibrary;

import java.awt.*;
//an extension of GameObject that describes moving entities.
//I intended to have static entities too, but I did not allows myself enough time.s
public abstract class MovingEntity extends GameObject{

    protected EntityController entityController;
    protected Movement movement;
    protected Direction direction;
    protected AnimationManager animationManager;

    protected Vector directionVector;

    public MovingEntity(EntityController entityController, SpriteLibrary spriteLibrary) {
        super();

        this.entityController = entityController;
        this.movement = new Movement(5);
        this.direction = Direction.E;
        this.directionVector = new Vector(0, 0);

        this.animationManager = new AnimationManager(spriteLibrary.getSpriteSet("player")); //make base sprite

    }

    @Override
    public void update(State state){
        super.update(state);

        movement.update(entityController);
        handleMotion();

        animationManager.update(direction);

        handleCollisions(state);
        manageDirection();

        animationManager.playAnimation(decideAnimation());

        position.apply(movement);

    }

    private void handleCollisions(State state) {

        state.getCollidingGameObjects(this).forEach(this::handleCollision);

    }

    protected abstract void handleCollision(GameObject gameObject);
    protected abstract void handleMotion();

    //if nextMotion is true, returns the next update's collision box, else this update's.
    @Override
    public CollisionBox getCollisionBox(Boolean nextMotion) {

        Position positionAfterNextMotion = Position.copyOf(getPosition());
        positionAfterNextMotion.apply(movement);

        if(nextMotion){

            return new CollisionBox(

                    new Rectangle(

                            positionAfterNextMotion.intX() - collisionBoxOffset.intX(),
                            positionAfterNextMotion.intY() - collisionBoxOffset.intY(),
                            collisionBoxSize.getWidth(),
                            collisionBoxSize.getHeight()

                    )

            );

        } else {

            return new CollisionBox(

                    new Rectangle(

                            position.intX() - collisionBoxOffset.intX(),
                            position.intY() - collisionBoxOffset.intY(),
                            collisionBoxSize.getWidth(),
                            collisionBoxSize.getHeight()

                    )

            );

        }

    }

    //was going to be used, but never ended up in the final game
    public boolean willCollideX(GameObject other){

        CollisionBox otherBox = other.getCollisionBox(true);
        Position positionWithXApplied = Position.copyOf(position);
        positionWithXApplied.applyX(movement);

        return CollisionBox.of(

                positionWithXApplied,
                collisionBoxSize,
                collisionBoxOffset

        ).collidesWith(otherBox);

    }

    //was going to be used, but never ended up in the final game
    public boolean willCollideY(GameObject other){

        CollisionBox otherBox = other.getCollisionBox(true);
        Position positionWithYApplied = Position.copyOf(position);
        positionWithYApplied.applyY(movement);

        return CollisionBox.of(

                positionWithYApplied,
                collisionBoxSize,
                collisionBoxOffset

        ).collidesWith(otherBox);

    }

    protected abstract String decideAnimation();

    //updates direction based on motion
    private void manageDirection() {

        if (movement.isMoving()) {

            this.direction = Direction.fromMotion(movement, this.direction);
            this.directionVector = Vector.vectorFromDirection(this.direction);

        }

    }

    @Override
    public Image getSprite() {

        return animationManager.getSprite();

    }

    public Object getController() {

        return entityController;

    }

    public boolean isIdle() {

        return !movement.isMoving();

    }

    //returns whether this entity is facing another position
    public boolean isFacing(Position other){

        Vector direction = Vector.directionBetweenPositions(other, getPosition());

        double dotProduct = Vector.dotProduct(direction, directionVector);

        return dotProduct > 0;

    }

    public Direction getDirection() {

        return direction;

    }

}
