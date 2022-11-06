package com.state.menu.ui;

import com.core.Size;
import com.state.game.GameState;
import com.state.menu.MenuState;
import com.ui.*;
import com.ui.clickable.UIButton;

import java.awt.*;

//The main menu shown when the game is launched
public class UIMainMenu extends UIMenu {

    public UIMainMenu(Size windowSize) {
        super(windowSize);

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        setCenterChildren(true);
        setBackgroundColor(Color.DARK_GRAY);

        //the name of the game
        //I wanted the player to have a dash ability, but that never made it in.
        UIText DashenText = new UIText("DASHEN");
        DashenText.setMargin(new Spacing(0, 0, 6, 0));
        addUIComponent(DashenText);
        addUIComponent(new UIButton("Play", (state) -> state.setNextState(new GameState(windowSize, state.getInput(), state.getAudioSettings()))));
        addUIComponent(new UIButton("Options", (state) -> ((MenuState)state).enterMenu(new UIOptionMenu(windowSize, state.getAudioSettings()))));
        addUIComponent(new UIButton("Scores", (state) -> ((MenuState)state).enterMenu(new UIScoreMenu(windowSize))));
        addUIComponent(new UIButton("Exit", (state) -> System.exit(0)));

    }

}
