package com.core;

import java.awt.*;
/*
Collision boxes for gameObjects allow the detection of entity collisions.
 */
public class CollisionBox {

    public static CollisionBox of(Position position, Size size, Position offset) {

        return new CollisionBox(

                new Rectangle(

                        position.intX() - offset.intX(),
                        position.intY() - offset.intY(),
                        size.getWidth(),
                        size.getHeight()

                )

        );

    }

    private final Rectangle bounds;

    public CollisionBox(Rectangle bounds) {

        this.bounds = bounds;

    }

    public boolean collidesWith(CollisionBox other){

        return bounds.intersects(other.getBounds());

    }

    public Rectangle getBounds() {

        return bounds;

    }

}
