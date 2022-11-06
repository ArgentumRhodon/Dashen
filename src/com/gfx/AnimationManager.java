package com.gfx;

import com.core.Direction;
import com.game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
/*
This is the animation manager. It... manages the current animation.
This class is responsible for timing which frame the current
animation is on.
 */
public class AnimationManager {

    private final SpriteSet spriteSet;
    private String currentAnimationName;
    private BufferedImage currentAnimationSheet;

    private final int updatesPerFrame;
    private int currentFrameTime;
    private int frameIndex;
    private int directionIndex;

    private final boolean looping;

    public AnimationManager(SpriteSet spriteSet) {

        this(spriteSet, true);

    }

    public AnimationManager(SpriteSet spriteSet, boolean looping) {

        this.spriteSet = spriteSet;
        this.looping = looping;

        this.updatesPerFrame = 4;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        this.directionIndex = 0;

        currentAnimationName = "";

    }

    //returns the sprite at the appropriate section of the sprite sheet.
    public Image getSprite(){

        try{

            if(frameIndex >= currentAnimationSheet.getWidth() / Game.SPRITE_SIZE){

                frameIndex = 0;

            }

            return currentAnimationSheet.getSubimage(

                    frameIndex * Game.SPRITE_SIZE,
                    directionIndex * Game.SPRITE_SIZE,
                    Game.SPRITE_SIZE,
                    Game.SPRITE_SIZE

            );

        }catch (NullPointerException e){

            frameIndex = 0;
            return null;

        }

    }

    //if looping is false, the animation does not reset.
    //else this just updates the frame index.
    public void update(Direction direction){

        currentFrameTime++;
        directionIndex = direction.getAnimationRow();

        if(currentFrameTime >= updatesPerFrame){

            currentFrameTime = 0;
            frameIndex++;

            int animationSize = currentAnimationSheet.getWidth() / Game.SPRITE_SIZE;
            if(frameIndex >= animationSize){

                frameIndex = looping ? 0 : animationSize - 1;

            }

        }

    }

    //sets the current animation.
    public void playAnimation(String name){

        if(!name.equals(currentAnimationName)){

            this.currentAnimationSheet = (BufferedImage) spriteSet.get(name);
            currentAnimationName = name;
            //if new animation, refresh the position to the first frame of said animation
            frameIndex = 0;

        }

    }

}
