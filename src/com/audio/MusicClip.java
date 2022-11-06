package com.audio;

import com.game.settings.AudioSettings;

import javax.sound.sampled.Clip;
//a music clip using music volume
public class MusicClip extends AudioClip{

    public MusicClip(Clip CLIP) {
        super(CLIP);


    }

    @Override
    protected float getVolume(AudioSettings audioSettings) {

        return audioSettings.getMusicVolume();

    }

}
