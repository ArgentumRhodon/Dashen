package com.entity;

import com.core.CollisionBox;
import com.core.Position;
import com.core.Size;
import com.entity.sentientEntity.SentientEntity;
import com.gfx.ImageLoader;
import com.state.State;

import java.awt.*;
import java.awt.image.BufferedImage;
//this is the healthbar of it's parent object.
public class HealthBar extends GameObject {

    //the ratio of the health bar that is filled from 0 to 1
    private double ratio = 1;

    public HealthBar() {

        size = new Size(50, 10);

        this.renderOffset = new Position(size.getWidth() / 2, size.getHeight() - 10);

    }

    @Override
    public void update(State state){
        super.update(state);

        ratio = (double)((SentientEntity) parent).getHealthPoints() / ((SentientEntity) parent).getMaxHealthPoints();

    }

    //returns a rectangle filled with red when full.
    @Override
    public Image getSprite() {

        BufferedImage sprite = (BufferedImage) ImageLoader.createCompatibleImage(size, ImageLoader.ALPHA_BIT_MASKED);

        if(ratio < 1 && ratio > 0){

            //this allows one to draw on a buffered image even if blank thus custom images
            Graphics2D graphics = sprite.createGraphics();

            graphics.setColor(Color.RED);
            graphics.fillRect(1, 1, (int)(size.getWidth() * ratio) - 2, size.getHeight() - 2);

            graphics.setColor(Color.GRAY);
            graphics.fillRect((int)(size.getWidth() * ratio) - 1, 1, (int)(size.getWidth() * (1-ratio)) - 2, size.getHeight() - 2);

        }

        return sprite;

    }

    @Override
    protected void setCollisionBox() {

        collisionBoxSize = new Size(0, 0);

    }

    @Override
    public CollisionBox getCollisionBox(Boolean nextMotion) {

        return new CollisionBox(new Rectangle(0, 0, 0, 0));

    }
}
