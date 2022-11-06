package com.state.game.ui;

import com.core.Size;
import com.state.State;
import com.ui.Alignment;
import com.ui.container.HorizontalContainer;
import com.ui.UIText;
//Not used, but functional. Shows time since start of gameState.
public class UIGameTime extends HorizontalContainer {

    private final UIText gameTime;

    public UIGameTime(Size windowSize) {
        super(windowSize);

        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.END);
        this.gameTime = new UIText("");

        addUIComponent(gameTime);

    }

    @Override
    public void update(State state){
        super.update(state);

        gameTime.setText(state.getTime().getFormattedTime());

    }

}
