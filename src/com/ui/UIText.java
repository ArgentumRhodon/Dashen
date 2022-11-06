package com.ui;

import com.core.Size;
import com.state.State;
import com.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//A UI Text, which is just a custom, rendered String with dropshadow
public class UIText extends UIComponent {

    private String text;
    private final float fontSize;
    private final int fontStyle;
    private final String fontFamily;
    private Color color;

    private final boolean dropShadow;
    private final int dropShadowOffset;
    private Color shadowColor;

    private Font font;

    public UIText(String text) {

        //The font I use. The only font of this type that would properly center.
        createFontFromFile("joystix monospace.ttf");

        this.text = text;

        this.fontSize = 24f;
        this.fontStyle = Font.PLAIN;
        this.fontFamily = "JoystixMonospace-Regular";
        this.color = Color.WHITE;

        this.dropShadow = true;
        this.dropShadowOffset = 2;
        this.shadowColor = new Color(100, 100, 100);

    }

    //creates a custom font from a font file.
    private void createFontFromFile(String fileName){

        try{

            font = Font.createFont(Font.TRUETYPE_FONT, new File(fileName)).deriveFont(fontSize);
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(font);

        } catch (IOException | FontFormatException e){

            System.err.println("Unable to create new font from file \"" + fileName + "\".");

        }

    }

    @Override
    public Image getSprite() {

        BufferedImage image = (BufferedImage) ImageLoader.createCompatibleImage(size, ImageLoader.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();

        graphics.setFont(font);

        if(dropShadow){

            graphics.setColor(shadowColor);
            graphics.drawString(text, padding.getLeft() + dropShadowOffset, fontSize + padding.getTop() + dropShadowOffset);

        }

        graphics.setColor(color);

        graphics.drawString(text, padding.getLeft(), fontSize + padding.getTop());

        graphics.dispose();

        return image;

    }

    @Override
    public void update(State state) {

        this.color = Color.WHITE;
        this.shadowColor = new Color(0x583717);

        updateFont();
        calculateSize();

    }

    //font metrics for size calculation
    private void calculateSize() {

        FontMetrics fontMetrics = new Canvas().getFontMetrics(font);

        int width = fontMetrics.stringWidth(text) + padding.getHorizontal();
        int height = fontMetrics.getHeight() + padding.getVertical();

        if(dropShadow){

            width += dropShadowOffset;
            height += dropShadowOffset;

        }

        size = new Size(width, height);

    }

    private void updateFont() {

        font = new Font(fontFamily, fontStyle, (int)fontSize);

    }

    public void setText(String text) {

        this.text = text;

    }

    public void setColor(Color color) {

        this.color = color;

    }

    public void setShadowColor(Color shadowColor) {

        this.shadowColor = shadowColor;

    }

}
