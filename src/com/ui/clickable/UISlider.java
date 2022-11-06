package com.ui.clickable;

import com.core.Size;
import com.gfx.ImageLoader;
import com.state.State;
import com.ui.Spacing;

import java.awt.*;
import java.awt.image.BufferedImage;
//A slider that controls a value.
//Only implementation is volume control, but could possible controll something else
public class UISlider extends UIClickable{

    private double value;
    private final double min;
    private final double max;

    public UISlider(double min, double max) {

        this.max = max;
        this.min = min;
        this.value = max;

        this.size = new Size(200, 10);

        this.margin = new Spacing(0, 0, 15, 0);

    }

    @Override
    protected void onFocus(State state) {

    }

    @Override
    public void onDrag(State state) {

        this.value = getValueAt(state.getInput().getMousePosition().getX());

        clamp();

    }

    //makes sure the slider cannot be dragged outside the bounds.
    private void clamp() {

        this.value = Math.min(max, this.value);
        this.value = Math.max(min, this.value);

    }

    @Override
    public void onClick(State state) {}

    //returns the appropriate value at the spot the slider was dragged.
    private double getValueAt(double xPosition) {

        double positionOnSlider = xPosition - absolutePosition.getX();
        double ratio = positionOnSlider / size.getWidth();
        double range = max - min;

        return min + range * ratio;

    }

    //returns the corresponding x value of a value
    private int getXFromValue() {

        double range = max - min;
        double valueRange = value - min;

        return (int)((valueRange / range) * size.getWidth());

    }

    //draws a shadow bar, with a white bar going to the value
    @Override
    public Image getSprite() {

        BufferedImage sprite = (BufferedImage) ImageLoader.createCompatibleImage(size, ImageLoader.ALPHA_OPAQUE);
        Graphics2D graphics = sprite.createGraphics();

        graphics.setColor(new Color(0x583717));
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getXFromValue(), size.getHeight());

        graphics.dispose();
        return sprite;

    }

    public double getValue() {

        return value;

    }

    public void setValue(double value) {

        this.value = value;

    }
}
