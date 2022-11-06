package com.game;
//keeps time since start
//used in ai and implemented in an unused UI container
public class Time {

    private int updatesSinceStart;

    public Time() {

        this.updatesSinceStart = 0;

    }

    public int getUpdatesFromSeconds(int seconds){

        return seconds * Loop.UPDATES_PER_SECOND;

    }

    public void update(){

        updatesSinceStart++;

    }

    //meant for the UIGameTime, but never used. You can try adding in a new
    //UIGameTime if you want.
    public String getFormattedTime(){

        StringBuilder stringBuilder = new StringBuilder();

        int minutes = updatesSinceStart / Loop.UPDATES_PER_SECOND / 60;
        int seconds = updatesSinceStart / Loop.UPDATES_PER_SECOND % 60;

        if(minutes < 10){

            stringBuilder.append(0);

        }

        stringBuilder.append(minutes);
        stringBuilder.append(":");

        if(seconds < 10){

            stringBuilder.append(0);

        }

        stringBuilder.append(seconds);

        return stringBuilder.toString();

    }

}
