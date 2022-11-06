package com.state.game.ui;

import com.core.Size;
import com.gfx.ImageLoader;
import com.state.State;
import com.state.game.GameState;
import com.ui.container.HorizontalContainer;
import com.ui.UIText;

import java.awt.image.BufferedImage;
//The UI that shows the player's current score during the game.
public class UIScore extends HorizontalContainer {

    private final UIText score;

    public UIScore(Size windowSize) {
        super(windowSize);
        score = new UIText("0");

//        padding = new Spacing(8, 5);

        containerImage = (BufferedImage) ImageLoader.loadImage("/sprites/HUD/score.png");
        addUIComponent(score);

    }

    @Override
    public void update(State state){
        super.update(state);

        score.setText("Score: " + ((GameState) state).getScore());

    }

}
