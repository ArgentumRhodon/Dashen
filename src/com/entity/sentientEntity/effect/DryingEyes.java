package com.entity.sentientEntity.effect;

import com.entity.sentientEntity.SentientEntity;
import com.entity.sentientEntity.action.staticAction.Blink;
import com.entity.sentientEntity.action.staticAction.Death;
import com.game.Loop;
import com.state.State;
//The only effect. Causes the player to blink sometimes if idle
public class DryingEyes extends Effect{

    //the chance of blinking pretty much.
    private static final double BLINK_RATE = 1d / Loop.UPDATES_PER_SECOND / 3;

    public DryingEyes() {
        super(Integer.MAX_VALUE);



    }

    @Override
    public void update(State state, SentientEntity sentientEntity){
        super.update(state, sentientEntity);

        if(sentientEntity.isIdle() && Math.random() < BLINK_RATE){

            sentientEntity.perform(new Blink());

        }

    }



}
