package com.ui.clickable;

import com.core.Size;
import com.gfx.ImageLoader;
import com.state.State;
import com.ui.Spacing;
import com.ui.container.UIContainer;
import com.ui.UIText;
import com.ui.container.VerticalContainer;

import java.awt.*;
import java.awt.image.BufferedImage;
//A button that can be clicked and will do something when clicked
public class UIButton extends UIClickable {

    private final UIContainer container;
    private final UIText label;

    //the action it will perform when clicked
    private final ClickAction clickAction;

    //aesthetic image states
    private BufferedImage offFocus, onFocus;

    public UIButton(String label, ClickAction clickAction) {

        initializeImages();

        this.label = new UIText(label);
        this.clickAction = clickAction;

        setMargin(new Spacing(5, 0, 0, 0));

        container = new VerticalContainer(new Size(0, 0));
        container.setContainerImage(offFocus);
        container.setCenterChildren(true);
        container.addUIComponent(this.label);

        container.setFixedSize(new Size(200,  42));

    }

    protected void initializeImages() {

        offFocus = (BufferedImage) ImageLoader.loadImage("/sprites/menus/buttonUp.png");
        onFocus = (BufferedImage) ImageLoader.loadImage("/sprites/menus/buttonDown.png");

    }

    //when the mouse is on the button
    @Override
    protected void onFocus(State state) {

        state.getAudioPlayer().playSound("buttonHover");

    }

    @Override
    public void onDrag(State state) {

    }

    @Override
    public void onClick(State state) {

        if(hasFocus) {

            state.getAudioPlayer().playSound("buttonClick");
            clickAction.execute(state);

        }

    }

    @Override
    public void update(State state){
        super.update(state);

        container.update(state);
        size = container.getSize();

        container.setContainerImage(offFocus);
        if (hasFocus){

            container.setContainerImage(onFocus);

            //Instead of changing the label directly, I just make the front text
            //invisible and set the shadow to white. This makes it look like the text
            //is sinking in with the image of the button.
            label.setColor(new Color(0,0,0,0));
            label.setShadowColor(Color.WHITE);

        }

    }

    @Override
    public Image getSprite() {

        return container.getSprite();

    }

}
