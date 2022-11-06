package com.gfx;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
//This class holds all of the spriteSets
public class SpriteLibrary {

    //HASHMAP
    private final Map<String, SpriteSet> spriteSets;

    private final Map<String, Image> images;

    public SpriteLibrary() {

        spriteSets = new HashMap<>();

        images = new HashMap<>();

        loadSpritesFromDisk();

    }

    private void loadSpritesFromDisk(){

        loadSpriteSet();
        loadImages();

    }

    //This loads the sprites for units
    private void loadSpriteSet(){

        String[] folderNames = getFolderNames("/sprites/units");

        for(String folderName : folderNames){

            SpriteSet spriteSet = new SpriteSet();

            String pathToFolder = "/sprites/units" + "/" + folderName;

            String[] sheetsInFolder = getImagesInFolder(pathToFolder);

            for(String sheetName : sheetsInFolder){

                spriteSet.addSheet(

                        sheetName.substring(0, sheetName.length() - 4),
                        ImageLoader.loadImage(pathToFolder + "/" + sheetName)


                );

            }

            spriteSets.put(folderName, spriteSet);

        }

    }

    //this loads sprites for static visuals like tiles
    private void loadImages(){

        String[] imagesInFolder = getImagesInFolder("/sprites/tiles");

        for(String fileName : imagesInFolder){

            images.put(

                    fileName.substring(0, fileName.length() - 4),
                    ImageLoader.loadImage("/sprites/tiles" + "/" + fileName)

            );

        }

    }

    //takes in a folder and returns a String array of all names of non-directory
    //files in the given directory
    private String[] getImagesInFolder(String basePath) {

        URL resource = this.getClass().getResource(basePath);

        File file = new File(resource.getFile());

        //if is file, adds to list
        return file.list((current, name) -> new File(current, name).isFile());

    }

    //returns the names of any directory file in a given directory
    private String[] getFolderNames(String basePath) {

        URL resource = this.getClass().getResource(basePath);

        File file = new File(resource.getFile());

        //if is directory, adds to list
        return file.list((current, name) -> new File(current, name).isDirectory());

    }

    public SpriteSet getSpriteSet(String name) {

        return spriteSets.get(name);

    }

    //returns a random random game tile sprite
    public Image getRandomGameTile(){

        int a = (int)(Math.random()*4);

        return switch (a) {
            case 0 -> getImage("gameTile1");
            case 1 -> getImage("gameTile2");
            case 2 -> getImage("gameTile3");
            default -> getImage("gameTile4");
        };

    }

    public Image getImage(String name){

        return images.get(name);

    }

}
