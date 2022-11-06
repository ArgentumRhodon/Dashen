package com.gfx;

import com.core.Size;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
//This class loads an image from a given path.
public class ImageLoader {

    public static final int ALPHA_OPAQUE = 1;
    public static final int ALPHA_BIT_MASKED = 2;

    public static Image loadImage(String path){

        try{

            Image imageFromDisk = ImageIO.read(ImageLoader.class.getResource(path));

            BufferedImage compatibleImage = (BufferedImage) createCompatibleImage(

                    new Size(imageFromDisk.getWidth(null), imageFromDisk.getHeight(null)),
                    ALPHA_BIT_MASKED

            );

            Graphics2D graphics = compatibleImage.createGraphics();
            graphics.drawImage(imageFromDisk, 0,0,null);

            graphics.dispose();

            return compatibleImage;

        } catch (IOException e) {

            System.out.println("Could not load image from path \"" + path + "\"");

        }

        return null;

    }

    //For potential compatibility issues between devices.
    //Also used to create empty images of a specified size.
    public static Image createCompatibleImage(Size size, int transparency){

        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        return graphicsConfiguration.createCompatibleImage(size.getWidth(), size.getHeight(), transparency);

    }

}
