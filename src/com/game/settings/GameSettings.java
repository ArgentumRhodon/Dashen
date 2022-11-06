package com.game.settings;
/*
Settings for the game such as debug, gameSpeed, and renderSettings.
 */
public class GameSettings {

    private boolean debugMode;
    private double gameSpeedMultiplier;
    private final double gameSpeedMultiplierChange;

    public GameSettings(boolean debugMode) {

        this.debugMode = debugMode;
        gameSpeedMultiplier = 1;
        gameSpeedMultiplierChange = 1.5;

    }

    public boolean isDebugMode() {

        return debugMode;

    }

    public void toggleDebugMode() {

        debugMode = !debugMode;

    }

    //increases the game speed for debugging
    public void increaseGameSpeed(){

        gameSpeedMultiplier *= gameSpeedMultiplierChange;

    }

    //decreases the game speed for debugging
    public void decreaseGameSpeed(){

        gameSpeedMultiplier *= (1 / gameSpeedMultiplierChange);

    }

    public double getGameSpeedMultiplier() {

        return gameSpeedMultiplier;

    }

}
