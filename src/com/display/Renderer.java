package com.display;

import com.core.Position;
import com.game.Game;
import com.map.Map;
import com.state.State;

import java.awt.*;

/*
Responsible for rendering all of the visuals for the game.
Renders the map, the state's game objects, and the state's UI.
 */
public class Renderer {

    public void render(State state, Graphics graphics) {

        renderMap(state, graphics);
        renderGameObjects(state, graphics);
        renderUI(state, graphics);

    }

    //renders the map tiles that are within the view of the character
    private void renderMap(State state, Graphics graphics){

        Camera camera = state.getCamera();
        Map map = state.getGameMap();

        Position start = map.getViewableStartingGridPosition(camera);
        Position end = map.getViewableEndingGridPosition(camera);

        for(int x = start.intX(); x < end.intX(); x++){

            for(int y = start.intY(); y < end.intY(); y++){


                graphics.drawImage(

                        map.getTiles()[x][y].getSprite(),
                        x*Game.SPRITE_SIZE - camera.getPosition().intX(),
                        y*Game.SPRITE_SIZE - camera.getPosition().intY(),
                        null

                );


            }

        }

    }

    //renders the state's current game objects
    private void renderGameObjects(State state, Graphics graphics){

        Camera camera = state.getCamera();

        //makes sure only to draw objects that are within view range
        state.getGameObjects().stream()
                .filter(camera::isInView)
                .forEach(gameObject -> graphics.drawImage(

                        gameObject.getSprite(),
                        gameObject.getRenderPosition(camera).intX(),
                        gameObject.getRenderPosition(camera).intY(),
                        null

                ));

    }

    //renders the state's active UI
    private void renderUI(State state, Graphics graphics) {

        state.getUiContainers().forEach(uiContainer -> graphics.drawImage(

                uiContainer.getSprite(),
                uiContainer.getRelativePosition().intX(),
                uiContainer.getRelativePosition().intY(),
                null

        ));

    }

}
