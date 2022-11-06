package com.ui.container;

import com.core.Position;
import com.core.Size;
import com.ui.UIComponent;
//organizes UIComponents vertically
public class VerticalContainer extends UIContainer {

    public VerticalContainer(Size windowSize) {
        super(windowSize);

    }

    @Override
    protected Size calculateContentSize() {

        int combinedChildHeight = 0;
        int widestChildWidth = 0;

        for(UIComponent child : childrenUi){

            combinedChildHeight += child.getSize().getHeight() + child.getMargin().getVertical();

            if(child.getSize().getWidth() > widestChildWidth){

                widestChildWidth = child.getSize().getWidth();

            }

        }

        return new Size(widestChildWidth, combinedChildHeight);

    }

    //vertical calculations
    @Override
    protected void calculateContentPosition() {

        int currentY = padding.getTop();
        int currentX = padding.getLeft();

        for(UIComponent child : childrenUi){

            if(centerChildren){

                currentX = getSize().getWidth() / 2 - child.getSize().getWidth() / 2;

            }

            currentY += child.getMargin().getTop();

            child.setRelativePosition(new Position(currentX, currentY));

            child.setAbsolutePosition(new Position(
                    currentX + absolutePosition.intX(),
                    currentY + absolutePosition.intY()
            ));

            currentY += child.getSize().getHeight();
            currentY += child.getMargin().getBottom();

        }

    }
}
