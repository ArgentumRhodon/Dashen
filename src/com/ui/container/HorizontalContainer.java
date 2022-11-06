package com.ui.container;

import com.core.Position;
import com.core.Size;
import com.ui.UIComponent;

//Not really used much, but organizes UIComponents horizontally
public class HorizontalContainer extends UIContainer {

    public HorizontalContainer(Size windowSize) {
        super(windowSize);

    }

    //Calculates the size of the who container (including its children UI)
    @Override
    protected Size calculateContentSize() {

        int combinedChildWidth = 0;
        int tallestChildHeight = 0;

        for(UIComponent child : childrenUi){

            combinedChildWidth += child.getSize().getWidth() + child.getMargin().getHorizontal();

            if(child.getSize().getHeight() > tallestChildHeight){

                tallestChildHeight = child.getSize().getHeight();

            }

        }

        return new Size(combinedChildWidth, tallestChildHeight);

    }

    //horizontal calculations
    @Override
    protected void calculateContentPosition() {

        int currentX = padding.getLeft();
        int currentY = padding.getTop();

        for(UIComponent child : childrenUi){

            if(centerChildren){

                currentY = getSize().getHeight() / 2 - child.getSize().getHeight() / 2;

            }

            currentX += child.getMargin().getLeft();

            child.setRelativePosition(new Position(currentX, currentY));

            child.setAbsolutePosition(new Position(
                    currentX + absolutePosition.intX(),
                    currentY + absolutePosition.intY()
            ));

            currentX += child.getSize().getWidth();
            currentX += child.getMargin().getRight();

        }

    }
}
