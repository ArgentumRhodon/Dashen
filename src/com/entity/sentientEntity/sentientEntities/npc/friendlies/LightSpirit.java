package com.entity.sentientEntity.sentientEntities.npc.friendlies;

import com.controller.EntityController;
import com.core.Position;
import com.core.Size;
import com.gfx.AnimationManager;
import com.gfx.SpriteLibrary;

//This is the light-blue ghost you see around the map. They give you a
//diminishing score boost based on current score.
public class LightSpirit extends FriendlyNPC{

    public LightSpirit(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);

        animationManager = new AnimationManager(spriteLibrary.getSpriteSet("lightSpirit"));

        maxHealthPoints = 1;
        healthPoints = maxHealthPoints;

        //small collision box adjustment
        collisionBoxOffset.add(new Position(0, 4));
        size = new Size(24, 40);

    }

    @Override
    public String getDeathSound() {

        return "friendlyGet2";

    }


}
