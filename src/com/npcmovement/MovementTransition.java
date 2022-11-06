package com.npcmovement;

import com.npcmovement.state.MovementStates;
import com.entity.sentientEntity.sentientEntities.npc.NPC;
import com.state.State;
/*
A Class that makes Ai transitions work a little easier.

 */
public class MovementTransition {

    private final MovementStates nextState;
    private final MovementCondition condition;

    public MovementTransition(MovementStates nextState, MovementCondition condition) {
        this.nextState = nextState;
        this.condition = condition;
    }

    //returns if the condition is met.
    public boolean shouldTransition(State state, NPC currentCharacter){

        return condition.isMet(state, currentCharacter);

    }

    public MovementStates getNextState() {

        return nextState;

    }
}
