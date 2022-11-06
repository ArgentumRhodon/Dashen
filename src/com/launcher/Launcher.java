package com.launcher;

import com.game.Game;
import com.game.Loop;

public class Launcher {

    //set the final width and height of display
    public static final int DISPLAY_WIDTH = 800, DISPLAY_HEIGHT = 600;

    public static void main(String[] args) {

        //run a new instance of Loop which in turn runs a new Game
        new Thread(new Loop(new Game(DISPLAY_WIDTH, DISPLAY_HEIGHT))).start();

    }

}
