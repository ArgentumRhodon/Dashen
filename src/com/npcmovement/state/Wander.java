package com.npcmovement.state;

import com.npcmovement.MovementTransition;
import com.controller.NPCController;
import com.core.Position;
import com.entity.sentientEntity.sentientEntities.npc.NPC;
import com.state.State;

import java.util.ArrayList;
import java.util.List;
/*
The MovementState for wandering. It chooses a position and moves to it.
 */
public class Wander extends MovementState {

    //I was going to do more with this, but didn't have time.
    private final List<Position> targets;

    public Wander() {
        super();

        targets = new ArrayList<>();

    }

    //NPCs go to the STAND state after successfully wandering.
    @Override
    protected MovementTransition initializeTransition() {

        return new MovementTransition(MovementStates.STAND, ((state, currentCharacter) -> arrived(currentCharacter)));

    }

    //sends instructions to the NCP's controller, thus moving the NPC to the target position
    @Override
    public void update(State state, NPC currentCharacter) {

        if(targets.isEmpty()){

            targets.add(state.getRandomPosition());

        }

        NPCController controller = (NPCController) currentCharacter.getController();

        controller.moveToTarget(targets.get(0), currentCharacter.getPosition());

        if(arrived(currentCharacter)){

            controller.stop();

        }

    }

    //returns true if the specified NPC is in range of their target position
    private boolean arrived(NPC currentCharacter) {

        return currentCharacter.getPosition().isInRangeOf(targets.get(0));

    }

}
