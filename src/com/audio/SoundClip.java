package com.audio;

import com.game.settings.AudioSettings;
import javax.sound.sampled.Clip;
//a sound clip using sound volume
public class SoundClip extends AudioClip{

    public SoundClip(Clip CLIP) {
        super(CLIP);


    }

    @Override
    protected float getVolume(AudioSettings audioSettings) {

        return audioSettings.getSoundVolume();

    }

}
