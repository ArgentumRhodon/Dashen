package com.npcmovement.state;

import com.npcmovement.MovementTransition;
import com.entity.sentientEntity.sentientEntities.npc.NPC;
import com.state.State;
/*
The MovementState for standing, which makes the npc stand in place for
a specified amount of time. In this case, the time is 3 as seen at
the end of line 18.
 */
public class Stand extends MovementState {

    private int updatesAlive;

    //NPCs go to the WANDER state after successfully standing.
    @Override
    protected MovementTransition initializeTransition() {

        return new MovementTransition(MovementStates.WANDER, (((state, currentCharacter) -> updatesAlive >= state.getTime().getUpdatesFromSeconds(3))));

    }

    @Override
    public void update(State state, NPC currentCharacter) {

        updatesAlive++;

    }


}
