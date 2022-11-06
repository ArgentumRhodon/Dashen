package com.state.game.ui;

import com.core.Size;
import com.state.State;
import com.state.game.GameState;
import com.state.menu.MenuState;
import com.state.menu.ui.UIOptionMenu;
import com.ui.Alignment;
import com.ui.UIMenu;
import com.ui.clickable.UIButton;

import java.awt.*;
//The pause menu that pops up when you press "E".
public class UIPauseMenu extends UIMenu {

    public UIPauseMenu(Size windowSize) {
        super(windowSize);

        setCenterChildren(true);
        setBackgroundColor(Color.GRAY);
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        addUIComponent(new UIButton("Continue", State::togglePaused));
        addUIComponent(new UIButton("Restart", (state) -> state.setNextState(new GameState(windowSize, state.getInput(), state.getAudioSettings()))));
        addUIComponent(new UIButton("Options", (state) -> {

            state.getUiContainers().add(new UIOptionMenu(windowSize, state.getAudioSettings()));
            state.getUiContainers().remove(this);

        }));
        addUIComponent(new UIButton("Menu", (state) -> state.setNextState(new MenuState(windowSize, state.getInput(), state.getAudioSettings()))));
        addUIComponent(new UIButton("Exit", (state) -> System.exit(0)));

    }

}
