package com.entity.sentientEntity.sentientEntities.npc.friendlies;

import com.controller.EntityController;
import com.entity.GameObject;
import com.entity.sentientEntity.sentientEntities.Player;
import com.entity.sentientEntity.sentientEntities.npc.NPC;
import com.gfx.SpriteLibrary;
//A friendly NPC. Gives bonuses to the player when touched. Only score though given limited time.
public abstract class FriendlyNPC extends NPC {

    protected double scoreMultiplier;

    public FriendlyNPC(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);

        //a random multiplier between 1.05 and 1.5
        scoreMultiplier = 1.05 + (Math.random() * .45);

    }

    public double getScoreMultiplier() {

        return scoreMultiplier;

    }

    @Override
    public void handleCollision(GameObject other){

        if(other instanceof Player){

            shouldDispose = true;

        }

    }

}
