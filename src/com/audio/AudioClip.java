package com.audio;

import com.game.settings.AudioSettings;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;

/*
A class that allows for easy creation of new sound clips.
 */
public abstract class AudioClip {

    private final Clip CLIP;

    public AudioClip(Clip CLIP) {

        this.CLIP = CLIP;

        startClip();

    }

    //starts the clip
    private void startClip(){

        SwingUtilities.invokeLater(CLIP::start);

    }

    //updates the sound clip volumes given the current audiosettings
    public void update(AudioSettings audioSettings){

        setVolume(audioSettings);

    }

    //sets the volume (more accurately gain, but effectively volume) of the clip
    protected void setVolume(AudioSettings audioSettings) {

        final FloatControl control = (FloatControl) CLIP.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMaximum() - control.getMinimum();
        float gain = range * getVolume(audioSettings) + control.getMinimum();

        control.setValue(gain);

    }

    protected abstract float getVolume(AudioSettings audioSettings  );

    //self-explanatory
    public boolean hasFinishedPlaying(){

        return !CLIP.isRunning();

    }

    public void cleanup(){

        CLIP.close();

    }

}
