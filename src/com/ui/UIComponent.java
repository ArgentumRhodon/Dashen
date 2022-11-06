package com.ui;

import com.core.Position;
import com.core.Size;
import com.state.State;
import com.ui.container.UIContainer;

import java.awt.*;
//A UI Component is any element of UI
//The UI equivalent of the GameObject class
public abstract class UIComponent {

    protected Position relativePosition;
    protected Position absolutePosition;
    protected Size size;
    protected Spacing margin, padding;

    protected UIContainer parentContainer;

    public UIComponent() {

        //position in the parent
        relativePosition = new Position(0,0);
        //position on screen
        absolutePosition = new Position(0,0);

        size = new Size(1,1);
        margin = new Spacing(0);
        padding = new Spacing(0);

    }

    public abstract Image getSprite();
    public abstract void update(State state);

    public Position getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(Position relativePosition) {
        this.relativePosition = relativePosition;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Spacing getMargin() {
        return margin;
    }

    public void setMargin(Spacing margin) {
        this.margin = margin;
    }

    public void setPadding(Spacing padding) {
        this.padding = padding;
    }

    public void setAbsolutePosition(Position absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public void setParentContainer(UIContainer parentContainer) {

        this.parentContainer = parentContainer;

    }
}
