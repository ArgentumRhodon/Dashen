package com.entity.sentientEntity.action;

import com.audio.AudioPlayer;
import com.entity.sentientEntity.SentientEntity;
import com.state.State;
//An action that an NPC can perform. Unique animations and such.
public abstract class Action {

    protected int lifeSpanInSeconds;
    protected boolean soundPlaying;

    public abstract void update(State state, SentientEntity sentientEntity);

    public abstract boolean isDone();

    public abstract String getAnimationName();

    public abstract String getSoundName();

    public void playSound(AudioPlayer audioPlayer){

        if(!soundPlaying && getSoundName() != null){

            audioPlayer.playSound(getSoundName());
            soundPlaying = true;

        }

    }

}
