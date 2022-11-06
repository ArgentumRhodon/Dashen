package com.entity.sentientEntity.action.staticAction;

import com.entity.sentientEntity.SentientEntity;
import com.game.Loop;
import com.state.State;
//Just a little detail action that makes the pc blink sometimes when idle
public class Blink extends StaticAction{

    private int lifeSpanInSeconds;

    public Blink() {

        lifeSpanInSeconds = Loop.UPDATES_PER_SECOND / 8;

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

        return "blink";

    }

    @Override
    public String getSoundName() {
        return null;
    }

}
