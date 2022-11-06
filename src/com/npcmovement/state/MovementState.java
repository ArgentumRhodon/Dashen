package com.npcmovement.state;

import com.npcmovement.MovementTransition;
import com.entity.sentientEntity.sentientEntities.npc.NPC;
import com.state.State;
/*
This class is the super class for each Movement State.
I planned on this game being bigger, and thus having
more Movement States; however, I ran out of time and had
to cut the scope significantly.

Having the stand and wander states helps make the NPCs
a bit more manageable.
 */
public abstract class MovementState {

    private final MovementTransition transition;

    //sets the MovementState that each MovementState should transition to when done.
    public MovementState() {

        this.transition = initializeTransition();

    }

    protected abstract MovementTransition initializeTransition();
    public abstract void update(State state, NPC currentCharacter);

    //returns true if the MovementState has lived for as long as it should.
    public boolean shouldTransition(State state, NPC currentCharacter){

        return transition.shouldTransition(state, currentCharacter);

    }

    public MovementStates getNextState(){

        return transition.getNextState();

    }

}
