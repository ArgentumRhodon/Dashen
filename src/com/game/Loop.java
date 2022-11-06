package com.game;

import com.state.game.GameState;
/*

    The main loop of the game. Responsible for calling the parent
    render and update functions, managing update speed, and frame rate.

 */
public class Loop implements Runnable {

    //Ties the framerate and updaterate to 60/s.
    public static final int UPDATES_PER_SECOND = 60;

    //Field variable for easy access to the Game
    private final Game game;

    //Meant for handling update speed of course
    private final double updateRate = 1.0d/UPDATES_PER_SECOND;

    //For debug statistics in the console
    //Uncomment the printStats method call in run() to see in console
    private long nextStatTime;
    private int frameCounter;
    private int updateCounter;

    public Loop(Game game) {

        this.game = game;

    }

    /*

        The game timer
        Responsible for calling run() and update() at specified interval.

     */
    @Override
    public void run(){

        //Time logic
        //Whether the program is running
        boolean running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        /*
        May or may not be necessary
        Best to leave it
         */
        update();

        //The actual loop
        while(running) {

            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;

            /*
            If in debug mode (F3 while in-game), the update speed may be manipulated
            for testing purposes
             */
            if(game.getState() instanceof GameState){

                accumulator += lastRenderTimeInSeconds * ((GameState) game.getState()).
                                                            getGameSettings().
                                                            getGameSpeedMultiplier();

            } else {

                accumulator += lastRenderTimeInSeconds;

            }

            lastUpdate = currentTime;

            //binds the update and render call frequency
            if(accumulator >= updateRate){

                while(accumulator >= updateRate) {

                    update();
                    accumulator -= updateRate;

                }

                //if moved to "move-here", the framerate will become unlimited
                render();

            }

            //move-here

            //Uncomment to see running fps, ups, memory usage, and number of gameObjects
            printStats();

        }

    }

    //Prints fps, ups, memory, and state information
    private void printStats() {

        if(System.currentTimeMillis() > nextStatTime){

            System.out.printf("FPS: %d, UPS: %d%n", frameCounter, updateCounter);

            frameCounter = 0;
            updateCounter = 0;
            nextStatTime = System.currentTimeMillis() + 1000;

            long memoryInUse = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            System.out.println("Total Memory: " + Runtime.getRuntime().totalMemory());
            System.out.println("Memory in use: " + memoryInUse);
            System.out.println("Number of Game Objects: " + game.getState().getGameObjects().size());

        }

    }

    //The parent update method
    private void update() {

        game.update();
        updateCounter++;

    }

    //The parent render method
    private void render() {

        game.render();
        frameCounter++;

    }

}
