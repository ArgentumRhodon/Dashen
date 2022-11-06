package com.game;

import com.core.Size;
import com.display.Display;
import com.game.settings.AudioSettings;
import com.game.settings.Setting;
import com.state.game.GameState;
import com.state.State;
import com.input.Input;
import com.state.menu.MenuState;

/*
Handles the state, audio settings, input, and display
There can only be one state in focus at a time
 */
public class Game {

    //all gameObject sprites are 64x64 pixels
    public static int SPRITE_SIZE = 64;

    private final Display display;
    private State state;

    //initializes the field
    public Game(int width, int height) {

        Input input = new Input();

        display = new Display(width, height, input);

        AudioSettings audioSettings = new AudioSettings(new Setting<>(true));

        state = new MenuState(new Size(width, height), input, audioSettings);

    }

    //updates the state
    public void update(){

        state.update(this);

    }

    //renders the state in either debug mode or non-debug mode
    public void render(){

        if(state instanceof GameState){

            display.render(state, ((GameState) state).getGameSettings().isDebugMode());

        } else {

            display.render(state, false);

        }

    }

    //returns the active state
    public State getState(){

        return state;

    }

    //sets the active state
    public void setState(State nextState) {

        state.cleanup();
        state = nextState;

    }

}
