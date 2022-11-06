package com.entity.sentientEntity.action.staticAction;

import com.entity.sentientEntity.SentientEntity;
import com.game.Loop;
import com.state.State;
//When an NPC loses health, it will perform this action.
public class Death extends StaticAction{

    private int lifeSpanInSeconds;

    private final SentientEntity sentientEntity;

    public Death(SentientEntity sentientEntity) {

        lifeSpanInSeconds = Loop.UPDATES_PER_SECOND / 2;

        //a variable to cause the entity to be considered dead although still in the state
        sentientEntity.setDead(true);

        this.sentientEntity = sentientEntity;

    }

    @Override
    public void update(State state, SentientEntity sentientEntity) {

        lifeSpanInSeconds--;

    }

    @Override
    public boolean isDone() {

        return lifeSpanInSeconds == 0;

    }

    @Override
    public String getAnimationName() {

        return "death";

    }

    //returns the dying npc's specific death sound
    @Override
    public String getSoundName() {
        return sentientEntity.getDeathSound();
    }

}
