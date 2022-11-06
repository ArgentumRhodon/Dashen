package com.input.mouse;

import com.input.Input;
import com.state.State;
//Handles mouse input between different UIs
public class MouseHandler {

    //this prevents stuff like clicking on another UI after dragging off of another
    //and other stuff like that.
    private MouseUser currentUser;

    public void update(State state){

        final Input input = state.getInput();

        handleCurrentUser(state, input);

        cleanup(input);


    }

    //clears current user and mouse click
    private void cleanup(Input input) {

        if(!input.isMousePressed()){

            currentUser = null;

        }

        input.clearMouseClicked();

    }

    //calls the click and drag methods of the current user on action
    private void handleCurrentUser(State state, Input input) {

        if(currentUser != null){

            if(input.isMouseClicked())
                currentUser.onClick(state);
            else if(input.isMousePressed())
                currentUser.onDrag(state);

        }

    }

    public void setCurrentUser(MouseUser mouseUser) {

        if(currentUser == null){

            currentUser = mouseUser;

        }

    }
}
