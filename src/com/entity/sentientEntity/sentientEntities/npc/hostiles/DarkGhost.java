package com.entity.sentientEntity.sentientEntities.npc.hostiles;

import com.controller.EntityController;
import com.core.Size;
import com.gfx.AnimationManager;
import com.gfx.SpriteLibrary;
//The prominent enemy in the game. Looks like a weird ghost-spirit thing.
public class DarkGhost extends HostileNPC {

    public DarkGhost(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);

        animationManager = new AnimationManager(spriteLibrary.getSpriteSet("darkGhost"));

        maxHealthPoints = 2;
        healthPoints = maxHealthPoints;

        damage = 1;
        worth = 10;

        size = new Size(32, 36);

    }

    @Override
    public String getDeathSound() {
        return "ghostDeath";
    }

}
