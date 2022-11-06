package com.gfx;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
//This holds the animations for each object such as idle, run, death
//Pretty basic, so not much else to it
public class SpriteSet {

    private final Map<String, Image> animationSheets;

    public SpriteSet() {

        this.animationSheets = new HashMap<>();

    }

    public void addSheet(String name, Image animationSheet){

        animationSheets.put(name, animationSheet);

    }

    public Image get(String name){

        return animationSheets.get(name);

    }

}
