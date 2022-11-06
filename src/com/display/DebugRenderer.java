package com.display;

import com.core.CollisionBox;
import com.entity.sentientEntity.SentientEntity;
import com.state.State;

import java.awt.*;
/*
The debug renderer renders collision boxes, and entity health.
 */
public class DebugRenderer {

    public void render(State state, Graphics graphics) {

        Camera camera = state.getCamera();

        state.getGameObjects().stream()

                .filter(camera::isInView)
                .map(gameObject -> gameObject.getCollisionBox(false))
                .forEach(collisionBox -> drawCollisionBox(collisionBox, graphics, camera));

        state.getGameObjectsOfClass(SentientEntity.class)
                .forEach(sentientEntity -> drawHealth(sentientEntity, graphics, camera));

        String numObjects = String.valueOf(state.getNumberOfGameObjectsOfClass(SentientEntity.class));
        graphics.drawString(numObjects, 200, 20);

    }

    //draws the integer value of an entity's health
    private void drawHealth(SentientEntity entity, Graphics graphics, Camera camera){

        graphics.setColor(Color.GREEN);

        graphics.drawString(

                Integer.toString(entity.getHealthPoints()),
                entity.getPosition().intX() - camera.getPosition().intX(),
                entity.getPosition().intY() - camera.getPosition().intY()

        );

    }

    //draws a collision box
    private void drawCollisionBox(CollisionBox collisionBox, Graphics graphics, Camera camera){

        graphics.setColor(Color.red);

        graphics.drawRect(

                (int)collisionBox.getBounds().getX() - camera.getPosition().intX(),
                (int)collisionBox.getBounds().getY() - camera.getPosition().intY(),
                (int)collisionBox.getBounds().getWidth(),
                (int)collisionBox.getBounds().getHeight()

        );

    }

}
