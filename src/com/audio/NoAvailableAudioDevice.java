package com.audio;
//a custom exception that is thrown when no audio device is detected in the system
public class NoAvailableAudioDevice extends Exception{

    public NoAvailableAudioDevice(String message) {
        super(message);
    }

}
