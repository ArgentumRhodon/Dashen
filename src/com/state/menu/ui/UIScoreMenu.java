package com.state.menu.ui;

import com.core.Size;
import com.state.menu.MenuState;
import com.ui.*;
import com.ui.clickable.UIButton;

import java.awt.*;
//Container for the score reports.
public class UIScoreMenu extends UIMenu {

    public UIScoreMenu(Size windowSize) {
        super(windowSize);

        centerChildren = true;

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        setBackgroundColor(Color.DARK_GRAY);

        addUIComponent(new UIText("TOP SCORES"));
        addUIComponent(new UIScoreReport(windowSize));
        addUIComponent(new UIButton("Back", (state) -> ((MenuState)state).enterMenu(new UIMainMenu(windowSize))));

    }

}
