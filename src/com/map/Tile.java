package com.map;

import com.gfx.SpriteLibrary;

import java.awt.*;
//another simple class that holds a tile sprite
public class Tile {

    protected Image sprite;

    public Tile(SpriteLibrary spriteLibrary) {

        this.sprite = spriteLibrary.getRandomGameTile();

    }

    public Image getSprite(){

        return sprite;

    }

}
