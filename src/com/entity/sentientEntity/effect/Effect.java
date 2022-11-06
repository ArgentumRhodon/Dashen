package com.entity.sentientEntity.effect;

import com.entity.MovingEntity;
import com.entity.sentientEntity.SentientEntity;
import com.state.State;

//When given to an entity, can create unique... effects (speed, life, blinking...).
//I wish I had done more with this, but alas no time.
public abstract class Effect {

    //self-explanatory
    private int lifeSpanInUpdates;

    public Effect(int lifeSpanInUpdates) {

        this.lifeSpanInUpdates = lifeSpanInUpdates;

    }

    public void update(State state, SentientEntity entity){

        lifeSpanInUpdates--;

    }

    public boolean shouldDelete(){

        return lifeSpanInUpdates <= 0;

    }

}
