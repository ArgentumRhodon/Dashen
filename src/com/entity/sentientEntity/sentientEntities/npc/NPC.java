package com.entity.sentientEntity.sentientEntities.npc;

import com.npcmovement.MovementManager;
import com.controller.EntityController;
import com.core.Size;
import com.entity.GameObject;
import com.entity.sentientEntity.SentientEntity;
import com.entity.sentientEntity.sentientEntities.Player;
import com.game.Game;
import com.state.State;
import com.gfx.SpriteLibrary;

import java.util.Comparator;
import java.util.Optional;
//An NPC controlled by the NPCController
public abstract class NPC extends SentientEntity {

    private MovementManager movementManager;

    //implemented, but not actually used.
    private Player target;
    private double targetRange;


    public NPC(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);

        movementManager = new MovementManager();

        targetRange = 5 * Game.SPRITE_SIZE;

    }

    @Override
    public void update(State state){
        super.update(state);

        movementManager.update(state, this);

        handleTarget(state);

    }

    //manages what the npc considers its target
    private void handleTarget(State state) {

        Optional<Player> targetPlayer = findPlayer(state);

        if(targetPlayer.isPresent()){

            Player player = targetPlayer.get();

            if(!player.equals(target)){

                target = player;

            }

        } else {

            target = null;

        }

    }

    //finds the closest player (only one player though)
    private Optional<Player> findPlayer(State state) {

        return state.getGameObjectsOfClass(Player.class).stream()
                .filter(player -> getPosition().distanceTo(player.getPosition()) < targetRange)
                .filter(player -> isFacing(player.getPosition()))
                .min(Comparator.comparingDouble(player -> position.distanceTo(player.getPosition())));

    }

    @Override
    protected void setCollisionBox() {

        this.collisionBoxSize = new Size(28,36);

    }

    @Override
    protected void handleCollision(GameObject other) {



    }

}

