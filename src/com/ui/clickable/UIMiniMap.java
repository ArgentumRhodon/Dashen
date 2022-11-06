package com.ui.clickable;

import com.core.Position;
import com.core.Size;
import com.display.Camera;
import com.entity.GameObject;
import com.entity.sentientEntity.SentientEntity;
import com.game.Game;
import com.gfx.ImageLoader;
import com.map.Map;
import com.state.State;
import com.state.game.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/*
This is the minimap. It shows the whole game map as a 128x128 pixel
square in the top left in the game state. It draws each hostile npc
as a red dot, each friendly npc as a yellow dot, and the player as
a green dot. They look like arrows in the map, but that is just
the limitations of the drawOval method, not that they're supposed
to look like arrows.
 */
public class UIMiniMap extends UIClickable {

    private Position pixelOffset;
    private double ratio;
    private Rectangle cameraViewBounds;
    private Color color;
    private State state;

    public UIMiniMap(State state) {

        this.state = state;

        size = new Size(128, 128);
        cameraViewBounds = new Rectangle(0, 0, 0, 0);
        color = Color.GRAY;

        calculateRatio(state.getGameMap());

    }

    @Override
    public void update(State state){
        super.update(state);

        Camera camera = state.getCamera();
        cameraViewBounds = new Rectangle(

                (int) (camera.getPosition().getX() * ratio + pixelOffset.intX()),
                (int) (camera.getPosition().getY() * ratio + pixelOffset.intY()),
                (int) (camera.getSize().getWidth() * ratio),
                (int) (camera.getSize().getHeight() * ratio)

        );

        color = new Color(0, 0, 0, 0);
        if(hasFocus){

            color = Color.WHITE;

        }

        this.state = state;

    }

    //the ratio between the size of the minimap and the size of the state-map
    private void calculateRatio(Map map) {

        ratio = Math.min(

                (double) size.getWidth() / map.getWidth(),
                (double) size.getHeight() / map.getHeight()

        );

        int pixelsPerGrid = (int) Math.round(Game.SPRITE_SIZE * ratio);

        pixelOffset = new Position(

                (size.getWidth() - map.getTiles().length * pixelsPerGrid) / 2,
                (size.getHeight() - map.getTiles()[0].length * pixelsPerGrid) / 2

        );

    }

    @Override
    protected void onFocus(State state) {

    }

    //drags the camera to wherever the cursor is
    //mainly useful for debugging
    @Override
    public void onDrag(State state) {

        state.getCamera().clearFocus();

        Position mousePosition = Position.copyOf(state.getInput().getMousePosition());
        mousePosition.subtract(absolutePosition);
        mousePosition.subtract(pixelOffset);

        state.getCamera().setPosition(
                new Position(

                        mousePosition.getX() / ratio - cameraViewBounds.getSize().getWidth() / ratio / 2,
                        mousePosition.getY() / ratio - cameraViewBounds.getSize().getHeight() / ratio / 2

                )
        );

    }

    //when the mouse is released, the camera refocuses on the player
    @Override
    public void onClick(State state) {

        if(state instanceof GameState){

            state.getCamera().focusOn(((GameState) state).getPlayer());

        }

    }

    @Override
    public Image getSprite() {

        BufferedImage sprite = (BufferedImage) ImageLoader.createCompatibleImage(size, ImageLoader.ALPHA_BIT_MASKED);
        Graphics2D graphics = sprite.createGraphics();

        graphics.setColor(color);

        //a rectangle representing the view bounds
        graphics.draw(cameraViewBounds);

        //a tracker for each game object
        drawGameObjectTrackers(graphics);

        if(((GameState) state).getGameSettings().isDebugMode())
            drawSpawnBoxes(graphics);

        graphics.dispose();
        return sprite;

    }

    //draws the boxes in which the npcs can spawn
    private void drawSpawnBoxes(Graphics2D graphics) {

        Map map = state.getGameMap();

        graphics.setColor(Color.WHITE);

        List<Rectangle> spawnAreas = new ArrayList<>();

        spawnAreas.add(new Rectangle(

                0,
                0,
                (int) cameraViewBounds.getX(),
                (int) (cameraViewBounds.getY() + cameraViewBounds.getHeight())

        ));

        spawnAreas.add(new Rectangle(

                (int) cameraViewBounds.getX(),
                0,
                (int) cameraViewBounds.getWidth(),
                (int) cameraViewBounds.getY()

        ));

        spawnAreas.add(new Rectangle(

                (int) (cameraViewBounds.getX() + cameraViewBounds.getWidth()),
                0,
                (int)(map.getWidth()*ratio) - (int) (cameraViewBounds.getX() + cameraViewBounds.getWidth()) - 1,
                (int)(map.getWidth()*ratio) - 1

        ));

        spawnAreas.add(new Rectangle(

                0,
                (int) (cameraViewBounds.getY() + cameraViewBounds.getHeight()),
                (int) (cameraViewBounds.getX() + cameraViewBounds.getWidth()),
                (int)(map.getWidth()*ratio) - (int) (cameraViewBounds.getY() + cameraViewBounds.getHeight()) - 1


        ));

        spawnAreas.forEach(graphics::draw);

    }

    //draws small dots where each game object is located in the minimap
    private void drawGameObjectTrackers(Graphics graphics){

        state.getGameObjectsOfClass(SentientEntity.class).forEach(gameObject -> {

            graphics.setColor(determineColor(gameObject));


            graphics.fillOval(

                    (int) (gameObject.getPosition().getX() * ratio) + pixelOffset.intX() - 2,
                    (int) (gameObject.getPosition().getY() * ratio) + pixelOffset.intY() - 2,
                    4,
                    4

            );

        });

    }

    //changed the color based off of which object is being drawn
    private Color determineColor(GameObject gameObject) {

        CLAZZ clazz;

        try{

            clazz = CLAZZ.valueOf(gameObject.getClass().getSuperclass().getSimpleName());

        }catch (IllegalArgumentException e){

            clazz = CLAZZ.valueOf(gameObject.getClass().getSimpleName());

        }

        //SWITCH ENUM
        return switch (clazz) {
            case Player -> Color.GREEN;
            case FriendlyNPC -> Color.YELLOW;
            case HostileNPC -> new Color(198, 15, 15);
        };

    }

    //a small enum for each possible general gameObject type
    enum CLAZZ{

        Player,
        FriendlyNPC,
        HostileNPC

    }

}
