package com.entity.sentientEntity.action.movingAction;

import com.entity.dumbEntity.spells.Spell;
import com.entity.sentientEntity.SentientEntity;
import com.state.State;
//"Casts" a new spell of an input type.
public class CastSpell extends MovingAction {

    private final Spell spellToCast;
    private boolean spellHasBeenCast = false;

    public CastSpell(Spell spellToCast){

        lifeSpanInSeconds = spellToCast.getUseTime();
        this.spellToCast = spellToCast;

    }

    @Override
    public void update(State state, SentientEntity sentientEntity) {
        lifeSpanInSeconds--;

        if(!spellHasBeenCast){

            castSpell(state);
            spellHasBeenCast = true;

        }

    }

    private void castSpell(State state){

        state.spawn(spellToCast);

    }

    @Override
    public boolean isDone() {

        return lifeSpanInSeconds == 0;

    }

    @Override
    public String getAnimationName() {

        return "";

    }

    @Override
    public String getSoundName() {

        return spellToCast.getSoundName();

    }

}
