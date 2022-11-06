package com.entity.sentientEntity;

import com.controller.EntityController;
import com.core.Position;
import com.entity.GameObject;
import com.entity.HealthBar;
import com.entity.MovingEntity;
import com.entity.sentientEntity.action.Action;
import com.entity.sentientEntity.action.staticAction.Death;
import com.entity.sentientEntity.action.staticAction.StaticAction;
import com.entity.sentientEntity.effect.Effect;
import com.state.State;
import com.gfx.SpriteLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//An entity that can move and/or perform an action
public abstract class SentientEntity extends MovingEntity {

    protected List<Effect> effects;
    protected Optional<Action> action;
    protected HealthBar healthBar;

    protected int healthPoints, maxHealthPoints;

    protected boolean dead;

    public SentientEntity(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);

        dead = false;

        effects = new ArrayList<>();
        action = Optional.empty();

        this.renderOffset = new Position(size.getWidth() / 2, size.getHeight());
        this.collisionBoxOffset = new Position(collisionBoxSize.getWidth() / 2, collisionBoxSize.getHeight());

        healthBar = new HealthBar();
        healthBar.setParent(this);

    }

    public void update(State state){
        super.update(state);

        handleAction(state);

        effects.forEach(effect -> effect.update(state, this));

        cleanup();

        if(healthPoints <= 0){

            shouldDispose = true;

        }

        clampPosition(state);

    }

    //makes sure the entity can only be within the bounds of the game map.
    private void clampPosition(State state) {

        if(position.getX() < size.getWidth() / 2d)
            position.setX(size.getWidth() / 2d);
        if(position.getY() < size.getHeight())
            position.setY(size.getHeight());

        if(position.getX() > state.getGameMap().getWidth() - (size.getWidth() / 2d))
            position.setX(state.getGameMap().getWidth() - (size.getWidth() / 2d));
        if(position.getY() > state.getGameMap().getHeight())
            position.setY(state.getGameMap().getHeight());


    }

    public abstract String getDeathSound();

    //clears effects and action
    private void cleanup() {

        List.copyOf(effects).stream()
                .filter(Effect::shouldDelete)
                .forEach(effects::remove);

        if(action.isPresent() && action.get().isDone()){

            action = Optional.empty();

        }

    }

    //desides the current animation of the entity based on movement or action
    @Override
    protected String decideAnimation(){

        if(action.isPresent()){

            if(action.get() instanceof StaticAction){

                return action.get().getAnimationName();

            } else {

                if (movement.isMoving()) {

                    return "run";

                }

                return "idle";

            }

        } else if (movement.isMoving()) {

            return "run";

        }

        return "idle";

    }

    //if an action is present, it will be updated and a sound will play
    private void handleAction(State state) {

        action.ifPresent(value -> {

            value.update(state, this);
            value.playSound(state.getAudioPlayer());

        });

    }

    //will stop motion if a static action is present
    protected void handleMotion() {

        if(action.isPresent()){

            if(action.get() instanceof StaticAction){

                movement.stop(true, true);

            }

        }

    }

    public void perform(Action action) {

        this.action = Optional.of(action);

    }

    public void addEffect(Effect effect){

        effects.add(effect);

    }

    protected void clearEffects(){

        effects.clear();

    }

    public boolean isAffectedBy(Class<?> _class){

        return effects.stream()
                .anyMatch(effect -> _class.isInstance(effect));

    }

    public int getHealthPoints() {

        return healthPoints;

    }

    public int getMaxHealthPoints() {

        return maxHealthPoints;

    }

    public void takeDamage(int damage){

        healthPoints -= damage;

    }

    public HealthBar getHealthBar() {

        return healthBar;

    }

    @Override
    protected void setCollisionBox() {

    }

    @Override
    protected void handleCollision(GameObject gameObject) {

    }

    public Optional<Action> getAction() {

        return action;

    }

    public void setDead(boolean dead) {

        this.dead = dead;

    }

    public boolean isDead() {

        return dead;

    }

    public boolean isNotDying(){

        if(action.isPresent()){

            return !(action.get() instanceof Death);

        }

        return true;

    }

}
