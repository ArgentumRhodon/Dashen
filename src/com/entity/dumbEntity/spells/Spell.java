package com.entity.dumbEntity.spells;

import com.controller.ProjectileController;
import com.core.Position;
import com.entity.MovingEntity;
import com.state.State;
import com.gfx.AnimationManager;
import com.gfx.SpriteLibrary;
/*
A spell object. Considered a (and the only) projectile. This is what the player
    uses to kill enemies.

There is only one spell, as I ran out of time. I planed on 4 tiers of spells as you can
probably guess by the 4 folders, but I only had time to implement one.
 */
public abstract class Spell extends MovingEntity {

    //how often the spell may be cast while the space bar is held.
    protected int useTime;
    //the damage the spell inflicts on an enemy
    protected int damage;

    public Spell(ProjectileController controller, SpriteLibrary spriteLibrary, MovingEntity caster) {
        super(controller, spriteLibrary);

        shouldDispose = false;

        animationManager = new AnimationManager(spriteLibrary.getSpriteSet("projectiles"), false);

        setInitialPosition(caster);

        controller.setMotion(caster);

    }

    //sets the position to the caster's position
    private void setInitialPosition(MovingEntity caster) {
        setPosition(new Position(

                caster.getPosition().getX(),
                caster.getPosition().getY() - (caster.getCollisionBox(true).getBounds().getHeight() / 1.5

                )));
    }

    @Override
    public void update(State state){
        super.update(state);

        checkDispose(state);

    }

    //if the spell is outside the game bounds (not outside camera view), the spell is disposed.
    //this way, you can hurt enemies off-screen, but they still disappear so as not to hog memory.
    private void checkDispose(State state) {

        if(state.getGameMap().isOutOfBounds(this)){

            shouldDispose = true;

        }

        if(shouldDispose){

            state.removeObject(this);

        }

    }

    public int getUseTime() {

        return useTime;

    }

    //returns the name of the spell's use-sound
    public abstract String getSoundName();

    public int getDamage() {

        return damage;

    }

}
