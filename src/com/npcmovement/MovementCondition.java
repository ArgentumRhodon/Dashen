package com.npcmovement;

import com.entity.sentientEntity.sentientEntities.npc.NPC;
import com.state.State;
/*
Like the condition interface, but specifically for NPC states.
 */
public interface MovementCondition {

    boolean isMet(State state, NPC currentCharacter);

}
