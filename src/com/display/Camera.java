package com.display;

import com.core.Position;
import com.core.Size;
import com.entity.GameObject;
import com.game.Game;
import com.state.State;

import java.awt.*;
import java.util.Optional;

/*
The camera is responsible for creating the illusion of movement.
Instead of the character moving out of the screen, this makes sure that
    the character can freely move around the map in its entirety. It plays
    a big role in render position of gameObjects.
 */
public class Camera {

    /*
    This makes it so that sprites have a small space-buffer around the edge of the window so
    that they don't appear to blip in and out of existence.
     */
    private static final int SPRITE_RENDER_BOUNDS_EXTENSION = Game.SPRITE_SIZE;

    //the "position" of the camera
    private Position position;
    private final Size windowSize;

    //the view bounds of the camera relative to the game map
    private Rectangle viewBounds;

    //which object the camera is focused on/will "follow"
    private Optional<GameObject> objectWithFocus;

    //initialize fields
    public Camera(Size windowSize) {

        this.position = new Position(0, 0);
        this.objectWithFocus = Optional.empty();
        this.windowSize = windowSize;

        calculateViewBounds();


    }

    /*
    View bounds are just the camera's position plus the width and height of the
    screen plus the render-buffer. Since objects' positions are at their feet,
    the buffer needs be in only one direction.
     */
    private void calculateViewBounds() {

        viewBounds = new Rectangle(
                position.intX(),
                position.intY(),
                windowSize.getWidth() + SPRITE_RENDER_BOUNDS_EXTENSION,
                windowSize.getHeight() + SPRITE_RENDER_BOUNDS_EXTENSION);

    }

    //focuses on a specified target gameObject
    public void focusOn(GameObject object){

        this.objectWithFocus = Optional.of(object);

    }

    //clears rge focus of the camera
    public void clearFocus(){

        this.objectWithFocus = Optional.empty();

    }

    //updates the camera
    public void update(State state){

        if(objectWithFocus.isPresent()){

            Position objectPosition = objectWithFocus.get().getPosition();

            this.position.setX(objectPosition.getX() -  windowSize.getWidth() / 2d);
            this.position.setY(objectPosition.getY() - windowSize.getHeight() / 2d);

            clampWithinBounds(state);

        }

        calculateViewBounds();

    }

    //makes sure the camera cannot drift outside of the map bounds
    private void clampWithinBounds(State state) {

        if(position.getX() < 0){

            position.setX(0);

        }
        if(position.getY() < 0){

            position.setY(0);

        }

        if(position.getX() + windowSize.getWidth() > state.getGameMap().getWidth()){

            position.setX(state.getGameMap().getWidth() - windowSize.getWidth());

        }
        if(position.getY() + windowSize.getHeight() > state.getGameMap().getHeight()){

            position.setY(state.getGameMap().getHeight() - windowSize.getHeight());

        }

    }

    //returns true if the gameObject is in view of the camera

    public boolean isInView(GameObject gameObject) {

        return viewBounds.intersects(

                gameObject.getPosition().intX(), gameObject.getPosition().intY(),
                gameObject.getSize().getWidth(), gameObject.getSize().getHeight()

        );

    }

    public Position getPosition() {

        return position;

    }

    public Size getSize() {

        return windowSize;

    }

    public void setPosition(Position position) {

        this.position = position;

    }

    public Rectangle getViewBounds() {

        return viewBounds;

    }
}
