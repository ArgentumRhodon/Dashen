package com.entity.sentientEntity.sentientEntities;

import com.controller.EntityController;
import com.controller.ProjectileController;
import com.core.Position;
import com.core.Size;
import com.entity.GameObject;
import com.entity.dumbEntity.spells.tier1.SpellATier1;
import com.entity.sentientEntity.SentientEntity;
import com.entity.sentientEntity.action.movingAction.CastSpell;
import com.entity.sentientEntity.effect.DryingEyes;
import com.entity.sentientEntity.sentientEntities.npc.hostiles.HostileNPC;
import com.state.State;
import com.gfx.SpriteLibrary;
//What you play as
public class Player extends SentientEntity {

    public Player(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);

        setPosition(new Position(2048,2048));
        addEffect(new DryingEyes());

        movement.setSpeed(6);

        //instant death upon hit for more challenge
        maxHealthPoints = 1;
        healthPoints = maxHealthPoints;

        size = new Size(24, 52);

    }

    @Override
    public void update(State state){
        super.update(state);

        handleInput(state);

    }

    @Override
    public String getDeathSound() {

        return "playerDeath2";

    }

    //the only input action the player can perform unfortunately
    private void handleInput(State state) {

        if(entityController.isRequestingCastSpell() && action.isEmpty()){

            perform(new CastSpell(new SpellATier1(

                    new ProjectileController(),
                    state.getSpriteLibrary(),
                    this

            )));

        }

    }

    public void increaseHealth(){

        healthPoints++;

    }

    @Override
    protected void setCollisionBox() {

        this.collisionBoxSize = new Size(24,52);

    }

    @Override
    protected void handleCollision(GameObject other) {

        if(other instanceof HostileNPC){

            if(!((SentientEntity) other).isDead())
                takeDamage(((HostileNPC) other).getDamage());

        }

    }

}
