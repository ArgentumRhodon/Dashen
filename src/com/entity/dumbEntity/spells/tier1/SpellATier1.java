package com.entity.dumbEntity.spells.tier1;

import com.controller.ProjectileController;
import com.core.*;
import com.entity.GameObject;
import com.entity.MovingEntity;
import com.entity.dumbEntity.spells.Spell;
import com.game.Loop;
import com.state.State;
import com.gfx.SpriteLibrary;
//The only spell. Basic.
public class SpellATier1 extends Spell {

    public SpellATier1(ProjectileController controller, SpriteLibrary spriteLibrary, MovingEntity caster) {
        super(controller, spriteLibrary, caster);

        damage = 2;
        useTime = Loop.UPDATES_PER_SECOND / 4;

        renderOffset = new Position(size.getWidth() / 2, size.getHeight() / 2);
        collisionBoxOffset = new Position(16, 16);

        controller.setMotion(caster);

        movement.setSpeed(15);

    }

    @Override
    public void update(State state){
        super.update(state);



    }

    @Override
    public String getSoundName() {

        return "spellA1";

    }

    @Override
    protected void setCollisionBox() {

        collisionBoxSize = new Size(30, 30);

    }

    @Override
    protected void handleCollision(GameObject other) {



    }

    @Override
    protected void handleMotion() {

    }

    @Override
    protected String decideAnimation() {

        return "spellatier1";

    }

}
