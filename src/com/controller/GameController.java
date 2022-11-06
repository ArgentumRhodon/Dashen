package com.controller;

import com.game.settings.GameSettings;
import com.input.Input;
import com.state.game.GameState;
import com.state.menu.ui.UIOptionMenu;

import java.awt.event.KeyEvent;
/*
A controller for the whole game. Reads input and executes the appropriate
commands. Mainly used for the debug "state" and menu opening input (E) in the gamestate
 */
public class GameController {

    private final Input input;

    public GameController(Input input) {

        this.input = input;

    }

    public void update(GameState gameState){

        GameSettings settings = gameState.getGameSettings();

        //toggles debug mode if F3 is pressed
        if(input.isPressed(KeyEvent.VK_F3)){

            settings.toggleDebugMode();

        }

        //increases game speed when in debug mode
        if(input.isPressed(KeyEvent.VK_UP)){

            if(gameState.getGameSettings().isDebugMode()){

                settings.increaseGameSpeed();

            }

        }

        //decreases game speed when in debug mode
        if(input.isPressed(KeyEvent.VK_DOWN)){

            if(gameState.getGameSettings().isDebugMode()){

                settings.decreaseGameSpeed();

            }

        }

        //opens/closes menu system in game
        if(input.isPressed(KeyEvent.VK_E)){

            if(gameState.isPlaying() && !gameState.containsUIOfType(UIOptionMenu.class))
                gameState.togglePaused();

            if(gameState.containsUIOfType(UIOptionMenu.class)){

                gameState.removeUIOfType(UIOptionMenu.class);
                gameState.setPaused(false);

            }

        }

    }

}
