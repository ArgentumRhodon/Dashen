package com.state.game.ui;

import com.core.Size;
import com.state.game.GameState;
import com.state.menu.MenuState;
import com.ui.Alignment;
import com.ui.UIMenu;
import com.ui.UIText;
import com.ui.clickable.UIButton;

import java.awt.*;
//The game over menu for when you die.
public class UIGameOverMenu extends UIMenu {

    public UIGameOverMenu(Size windowSize, int score) {
        super(windowSize);

        setBackgroundColor(Color.BLACK);
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        addUIComponent(new UIText("Game Over"));
        addUIComponent(new UIText("Score: " + score));
        addUIComponent(new UIButton("New Game", (state) -> state.setNextState(new GameState(windowSize, state.getInput(), state.getAudioSettings()))));
        addUIComponent(new UIButton("Menu", (state) -> state.setNextState(new MenuState(windowSize, state.getInput(), state.getAudioSettings()))));
        addUIComponent(new UIButton("Exit", (state) -> System.exit(0)));

        setCenterChildren(true);

    }
}
