package com.ui.clickable;

import com.core.Size;
import com.game.settings.Setting;
import com.gfx.ImageLoader;
import com.state.State;
import com.ui.*;
import com.ui.container.HorizontalContainer;
import com.ui.container.UIContainer;

import java.awt.*;
import java.awt.image.BufferedImage;
//A toggle button
//Only use is for toggling audio
public class UIToggle extends UIComponent {

    private final UIContainer container;

    public UIToggle(String label, Setting<Boolean> setting) {

        this.container = new HorizontalContainer(new Size(0, 0));
        container.setCenterChildren(true);
        container.addUIComponent(new CheckBox(setting));
        container.addUIComponent(new UIText(label));

    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    public void update(State state) {

        container.update(state);
        size = container.getSize();
        container.setParentContainer(parentContainer);
        container.setAbsolutePosition(absolutePosition);

    }

    //a checkbox, which is like a button, but it toggles instead of presses
    private static class CheckBox extends UIClickable {

        private final Setting<Boolean> setting;

        public CheckBox(Setting<Boolean> setting) {

            this.setting = setting;
            size = new Size(30, 30);
            margin = new Spacing(0, 5, 0, 0);

        }

        @Override
        public void update(State state){
            super.update(state);

        }

        @Override
        protected void onFocus(State state) {}

        @Override
        public void onDrag(State state) {}

        @Override
        public void onClick(State state) {

            if(hasFocus)
                setting.setValue(!setting.getValue());

        }

        @Override
        public Image getSprite() {

            BufferedImage sprite;
            if(setting.getValue()){

                sprite = (BufferedImage) ImageLoader.loadImage("/sprites/menus/audioButtonUp.png");

            } else {

                sprite = (BufferedImage) ImageLoader.loadImage("/sprites/menus/audioButtonDown.png");

            }

            return sprite;

        }

    }

}
