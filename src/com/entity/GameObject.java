package com.entity;

import com.core.CollisionBox;
import com.core.Position;
import com.core.Size;
import com.display.Camera;
import com.entity.sentientEntity.SentientEntity;
import com.entity.sentientEntity.action.staticAction.Death;
import com.entity.sentientEntity.sentientEntities.npc.friendlies.FriendlyNPC;
import com.entity.sentientEntity.sentientEntities.npc.hostiles.HostileNPC;
import com.state.State;
import com.state.game.GameState;

import java.awt.*;
/*
The GameObject class. This is the top-super class of each enitity in the game.
Declares the position, renderOffset, collisionBox(offset), size, etc.
 */
public abstract class GameObject {

    protected Position position;
    protected Position renderOffset;
    protected Position collisionBoxOffset;
    protected Size size;
    protected CollisionBox collisionBox;
    protected Size collisionBoxSize;

    //If this is true, this instcance will be removed from the state.
    protected boolean shouldDispose;

    //render priority
    protected int renderLayer;

    //each game object can be parented to another, and as such will be "attacthed" to said parent.
    protected GameObject parent;

    public GameObject(){

        shouldDispose = false;

        position = new Position(0,0);
        renderOffset = new Position(0, 0);
        collisionBoxOffset = new Position(0, 0);
        size = new Size(64,64);
        collisionBox = new CollisionBox(null);

        renderLayer = 5;

        setCollisionBox();

    }

    public void update(State state){

        if(shouldDispose){

            //since sentient entities have child objects, this need to be checked so that
            //the child objects may be removed too.
            if(this instanceof SentientEntity){

                if(!((SentientEntity) this).isDead()){

                    ((SentientEntity) this).perform(new Death((SentientEntity) this));

                }

                if(((SentientEntity) this).isNotDying()){

                    //dispose of this object
                    state.removeObject(this);
                    //dispose of this object's health bar.
                    state.removeObject(((SentientEntity) this).getHealthBar());

                    if(this instanceof HostileNPC){

                        //adds to score
                        ((GameState) state).score += ((HostileNPC) this).getWorth();

                    } else if(this instanceof FriendlyNPC){

                        //multiplies score
                        ((GameState) state).score *= 1 + ( 100 * ((FriendlyNPC) this).getScoreMultiplier() / ((GameState) state).score);

                    }

                }

            } else {

                //else it is removed normally
                state.removeObject(this);

            }

        }

    }

    public abstract Image getSprite();
    protected abstract void setCollisionBox();

    public abstract CollisionBox getCollisionBox(Boolean nextMotion);

    public boolean collidesWith(GameObject other){

        return getCollisionBox(true).collidesWith(other.getCollisionBox(true));

    }

    //returns the a copy of the position instead of a reference to the position
    public Position getPosition() {

        Position finalPosition = Position.copyOf(position);

        if(parent != null){

            finalPosition.add(parent.getPosition());

        }

        return finalPosition;

    }

    public void setPosition(Position position) {

        this.position = position;

    }

    //gets the render position based on the camera
    public Position getRenderPosition(Camera camera) {

        return new Position(

                getPosition().getX() - camera.getPosition().getX() - renderOffset.getX(),
                getPosition().getY() - camera.getPosition().getY() - renderOffset.getY()

        );

    }

    public Size getSize() {

        return size;

    }

    public void setParent(GameObject parent) {

        this.parent = parent;

    }

    public int getRenderLayer() {

        return renderLayer;

    }

    public void setShouldDispose(boolean shouldDispose) {

        this.shouldDispose = shouldDispose;

    }

    public boolean isShouldDispose() {

        return shouldDispose;

    }
}
