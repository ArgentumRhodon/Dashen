package com.ui.container;

import com.core.Position;
import com.core.Size;
import com.state.State;
import com.gfx.ImageLoader;
import com.ui.Alignment;
import com.ui.Spacing;
import com.ui.UIComponent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
//contains UI components, but is a UI component itself.
//So, you can have containers in containers in containers.
//Underused since I ran out of time.
public abstract class UIContainer extends UIComponent {

    protected BufferedImage containerImage;

    protected boolean centerChildren;
    protected Color backgroundColor;

    protected Alignment alignment;
    protected Size windowSize;

    protected Size fixedSize;

    protected List<UIComponent> childrenUi;

    public UIContainer(Size windowSize) {
        super();

        containerImage = null;

        this.windowSize = windowSize;
        centerChildren = false;
        alignment = new Alignment(Alignment.Position.START, Alignment.Position.START);

        backgroundColor = new Color(0,0,0,0);
        margin = new Spacing(5);
        padding = new Spacing(5);

        childrenUi = new ArrayList<>();

        calculateSize();
        calculatePosition();

    }

    //calculates the size of its contents
    protected abstract Size calculateContentSize();
    //calculates the positions of its contents
    protected abstract void calculateContentPosition();

    //calculates the size of the container if not given a fixed size
    private void calculateSize(){

        Size calculatedContentSize = calculateContentSize();

        size = fixedSize != null

                ? fixedSize

                : new Size(

                padding.getHorizontal() + calculatedContentSize.getWidth(),
                padding.getVertical() + calculatedContentSize.getHeight()

        );

    }

    //calculates the position
    private void calculatePosition(){

        int x = padding.getLeft();
        if(alignment.getHORIZONTAL().equals(Alignment.Position.CENTER)){

            x = windowSize.getWidth() / 2 - size.getWidth() / 2;

        } else if (alignment.getHORIZONTAL().equals(Alignment.Position.END)){

            x = windowSize.getWidth() - size.getWidth() - margin.getRight();

        }

        int y = padding.getTop();
        if(alignment.getVERTICAL().equals(Alignment.Position.CENTER)){

            y = windowSize.getHeight() / 2 - size.getHeight() / 2;

        } else if (alignment.getVERTICAL().equals(Alignment.Position.END)){

            y = windowSize.getHeight() - size.getHeight() - margin.getBottom();

        }

        this.relativePosition = new Position(x, y);

        if(parentContainer == null){

            this.absolutePosition = new Position(x, y);

        }

        calculateContentPosition();

    }

    @Override
    public Image getSprite() {

        BufferedImage image = (BufferedImage) ImageLoader.createCompatibleImage(new Size(size.getWidth(), size.getHeight()), ImageLoader.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();

        //scales the image to the size so that I can use fewer assets for similarly-sized menus
        if(containerImage != null) {
            graphics.drawImage(containerImage.getScaledInstance(size.getWidth(), size.getHeight(), 0), 0, 0, null);
        } else {

            graphics.setColor(backgroundColor);
            graphics.fillRect(0,0, size.getWidth(), size.getHeight());

        }

        childrenUi.forEach(child -> graphics.drawImage(

                child.getSprite(),
                child.getRelativePosition().intX(),
                child.getRelativePosition().intY(),
                null

        ));

        graphics.dispose();
        return image;

    }

    @Override
    public void update(State state) {

        childrenUi.forEach(child -> child.update(state));

        calculateSize();
        calculatePosition();

    }

    public void addUIComponent(UIComponent uiComponent){

        childrenUi.add(uiComponent);
        uiComponent.setParentContainer(this);

    }

    public void setBackgroundColor(Color color) {

        backgroundColor = color;

    }

    public void setAlignment(Alignment alignment) {

        this.alignment = alignment;

    }

    public void setFixedSize(Size fixedSize) {

        this.fixedSize = fixedSize;

    }

    public void setCenterChildren(boolean centerChildren) {

        this.centerChildren = centerChildren;

    }

    public void setContainerImage(BufferedImage containerImage) {

        this.containerImage = containerImage;

    }

}
