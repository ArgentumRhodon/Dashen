package com.map;

import com.core.Position;
import com.core.Size;
import com.display.Camera;
import com.entity.GameObject;
import com.game.Game;
import com.gfx.SpriteLibrary;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
/*
The map for each state. This is a visual background/size of the game.
 */
public class Map {

    private final Size size;
    //Renders so many tiles outside of camera view.
    private static final int MAP_RENDER_BOUNDS_EXTENSION = 2;

    private final Tile[][] tiles;

    public Map(Size size, SpriteLibrary spriteLibrary) {

        this.size = size;
        tiles = new Tile[size.getWidth()][size.getHeight()];

        initializeTiles(spriteLibrary);

    }

    //if not within the bounds of the map returns true
    public boolean isOutOfBounds(GameObject object){

        return !getMapBox().contains(object.getCollisionBox(false).getBounds());

    }

    //returns the rectangle representing the size of the game map
    public Rectangle getMapBox(){

        int mapWidth = Game.SPRITE_SIZE*size.getWidth();
        int mapHeight = Game.SPRITE_SIZE*size.getHeight();

        return new Rectangle(

                0,
                0,
                mapWidth,
                mapHeight

        );

    }

    //sets the tiles for the map
    private void initializeTiles(SpriteLibrary spriteLibrary) {

        for(Tile[] row : tiles){

            for (int i = 0; i < row.length; i++) {

                row[i] = new Tile(spriteLibrary);

            }

        }

    }

    public Tile[][] getTiles(){

        return tiles;

    }

    public int getWidth(){

        return tiles.length * Game.SPRITE_SIZE;

    }

    public int getHeight(){

        return tiles[0].length * Game.SPRITE_SIZE;

    }

    //returns a random position outside of camera view within
    //a certain rectangle of NPC spawn.
    public Position getRandomPosition(Camera camera) {

        List<SpawnArea> spawnAreas = calculateSpawnAreas(camera);

        spawnAreas.sort(Comparator.comparing(SpawnArea::getArea));

        //since the rectangles vary by size depending on character
        //position, they get different chances of being picked based
        //on their sizes.
        List<Integer> spawnChance = new ArrayList<>();
        for (int i = 0; i < spawnAreas.size(); i++){

            //adds i elements of i to the list creating 0,1,1,2,2,2,3,3,3,3
            //thus giving 3, a larger rectangle, a greater chance of being picked
            //than 1, a smaller rectangle.
            for(int j = -1; j < i; j++){

                spawnChance.add(i);

            }

        }
        Collections.shuffle(spawnChance);

        SpawnArea spawnArea = spawnAreas.get(spawnChance.get(0));

        //a random specific position within the chosen spawn area
        double x = spawnArea.getX() + Math.random()*spawnArea.getWidth();
        double y = spawnArea.getY() + Math.random()*spawnArea.getHeight();

        return new Position(x, y);

    }

    //a bunch of math that calculates the spawn areas based on camera position/size
    private List<SpawnArea> calculateSpawnAreas(Camera camera) {

        List<SpawnArea> spawnAreas = new ArrayList<>();

        Rectangle viewBounds = camera.getViewBounds();
        spawnAreas.add(new SpawnArea(

                0,
                0,
                (int) viewBounds.getX(),
                (int) (viewBounds.getY() + viewBounds.getHeight())

        ));

        spawnAreas.add(new SpawnArea(

                (int) viewBounds.getX(),
                0,
                (int) viewBounds.getWidth(),
                (int) viewBounds.getY()

        ));

        spawnAreas.add(new SpawnArea(

                (int) (viewBounds.getX() + viewBounds.getWidth()),
                0,
                getWidth() - (int) (viewBounds.getX() + viewBounds.getWidth()) - 1,
                getWidth() - 1

        ));

        spawnAreas.add(new SpawnArea(

                0,
                (int) (viewBounds.getY() + viewBounds.getHeight()),
                (int) (viewBounds.getX() + viewBounds.getWidth()),
                getWidth() - (int) (viewBounds.getY() + viewBounds.getHeight()) - 1


        ));

        return spawnAreas.stream()
                .filter(rectangle -> rectangle.getWidth() > 64)
                .filter(rectangle -> rectangle.getHeight() > 64)
                .collect(Collectors.toList());

    }

    //gets the position at which the camera-view starts minus extension
    public Position getViewableStartingGridPosition(Camera camera) {

        return new Position(

                Math.max(0, camera.getPosition().getX() / Game.SPRITE_SIZE - MAP_RENDER_BOUNDS_EXTENSION),
                Math.max(0, camera.getPosition().getY() / Game.SPRITE_SIZE - MAP_RENDER_BOUNDS_EXTENSION)

        );

    }

    //gets the position at which the camera-view ends plus extension
    public Position getViewableEndingGridPosition(Camera camera) {

        return new Position(

                Math.min(tiles.length, camera.getPosition().getX() / Game.SPRITE_SIZE + camera.getSize().getWidth() / Game.SPRITE_SIZE + MAP_RENDER_BOUNDS_EXTENSION),
                Math.min(tiles[0].length, camera.getPosition().getY() / Game.SPRITE_SIZE + camera.getSize().getHeight() / Game.SPRITE_SIZE + MAP_RENDER_BOUNDS_EXTENSION)

        );

    }

}
