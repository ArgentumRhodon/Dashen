package com.ui.clickable;

import com.core.Position;
import com.input.mouse.MouseUser;
import com.state.State;
import com.ui.UIComponent;

import java.awt.*;
//A super class for every ui object that is clickable (interacts with the mouse)
public abstract class UIClickable extends UIComponent implements MouseUser {

    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state){

        Position mousePosition = state.getInput().getMousePosition();

        boolean previousFocusStatus = hasFocus;

        hasFocus = getBounds().contains(mousePosition.intX(), mousePosition.intY());
        isPressed = hasFocus && state.getInput().isMousePressed();

        if(!previousFocusStatus && hasFocus){

            onFocus(state);

        }

        if(hasFocus){

            state.getMouseHandler().setCurrentUser(this);

        }

    }

    protected abstract void onFocus(State state);

    private Rectangle getBounds(){

        return new Rectangle(

                absolutePosition.intX(),
                absolutePosition.intY(),
                size.getWidth(),
                size.getHeight()

        );

    }

}
