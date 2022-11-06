package com.npcmovement;

import com.npcmovement.state.MovementState;
import com.npcmovement.state.MovementStates;
import com.npcmovement.state.Stand;
import com.npcmovement.state.Wander;
import com.entity.sentientEntity.sentientEntities.npc.NPC;
import com.state.State;
/*
Manages the MovementState of an NPC
 */
public class MovementManager {

    private MovementState currentMovementState;

    public MovementManager() {

        transitionTo(MovementStates.WANDER);

    }

    public void update(State state, NPC currentCharacter){

        currentMovementState.update(state, currentCharacter);

        if(currentMovementState.shouldTransition(state, currentCharacter)){

            transitionTo(currentMovementState.getNextState());

        }

    }

    private void transitionTo(MovementStates nextState) {

        if (nextState == MovementStates.WANDER) {
            currentMovementState = new Wander();
        } else {
            currentMovementState = new Stand();
        }

    }


}
