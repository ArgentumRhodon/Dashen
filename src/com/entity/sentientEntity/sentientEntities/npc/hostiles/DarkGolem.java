package com.entity.sentientEntity.sentientEntities.npc.hostiles;

import com.controller.EntityController;
import com.core.Movement;
import com.core.Size;
import com.gfx.AnimationManager;
import com.gfx.SpriteLibrary;
//A more powerful enemy. Faster than you and has 10 health.
public class DarkGolem extends HostileNPC {

    public DarkGolem(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);

        animationManager = new AnimationManager(spriteLibrary.getSpriteSet("darkGolem"));

        maxHealthPoints = 10;
        healthPoints = maxHealthPoints;
        damage = 2;

        worth = 100;

        movement = new Movement(7);

        size = new Size(24, 60);

    }

    @Override
    public String getDeathSound() {
        return "golemDeath";
    }

    @Override
    protected void setCollisionBox(){

        collisionBoxSize = new Size(24, 60);

    }

}
