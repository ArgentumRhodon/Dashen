package com.ui;

import com.core.Size;
import com.gfx.ImageLoader;
import com.ui.container.VerticalContainer;

import java.awt.image.BufferedImage;
//A container class meant to give each menu container some default code.
public class UIMenu extends VerticalContainer {

    public UIMenu(Size windowSize) {
        super(windowSize);

        containerImage = (BufferedImage) ImageLoader.loadImage("/sprites/menus/menu.png");

        setPadding(new Spacing(15, 20, 20, 20));

    }

}
