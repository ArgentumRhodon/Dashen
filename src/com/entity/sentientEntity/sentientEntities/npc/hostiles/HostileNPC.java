package com.entity.sentientEntity.sentientEntities.npc.hostiles;

import com.controller.EntityController;
import com.entity.GameObject;
import com.entity.dumbEntity.spells.Spell;
import com.entity.sentientEntity.sentientEntities.npc.NPC;
import com.gfx.SpriteLibrary;
//An npc that can deal and take damage.
public abstract class HostileNPC extends NPC {

    //the damage it deals
    protected int damage;
    //the number of points gained when killed
    protected int worth;

    public HostileNPC(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);

        worth = 10;

    }

    @Override
    protected void handleCollision(GameObject other){
        super.handleCollision(other);

        if(other instanceof Spell){

            takeDamage( ((Spell) other).getDamage());

            if(!isDead())
                other.setShouldDispose(true);

        }

    }

    public int getDamage() {

        return damage;

    }

    public int getWorth() {

        return worth;

    }

}
