package com.controller;

import com.input.Input;

import java.awt.event.KeyEvent;
//a controller for player movement based on input
public class PlayerController implements EntityController {

    private final Input input;

    public PlayerController(Input input){

        this.input = input;

    }

    @Override
    public boolean isRequestingUp() {

        return input.isCurrentlyPressed(KeyEvent.VK_W);

    }

    @Override
    public boolean isRequestingDown() {

        return input.isCurrentlyPressed(KeyEvent.VK_S);


    }

    @Override
    public boolean isRequestingLeft() {

        return input.isCurrentlyPressed(KeyEvent.VK_A);

    }

    @Override
    public boolean isRequestingCastSpell() {

        //eventually mouse input
        return input.isCurrentlyPressed(KeyEvent.VK_SPACE);

    }

    @Override
    public boolean isRequestingRight() {

        return input.isCurrentlyPressed(KeyEvent.VK_D);

    }

}
