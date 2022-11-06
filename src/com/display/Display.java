package com.display;

import com.state.State;
import com.gfx.ImageLoader;
import com.input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/*
Responsible for the canvas and frame, the Display class holds the Renderer, which
renders all of the state's assets. It also handles the Debug Renderer, which displays
hitboxes, spawnChunks in the minimap, and npc health.
 */
public class Display extends JFrame {

    private final Renderer renderer;
    private final DebugRenderer debugRenderer;
    private final Canvas canvas;

    //creates the frame and canvas
    public Display(int width, int height, Input input){

        setTitle("Dashen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setAesthetics();

        this.renderer = new Renderer();
        this.debugRenderer = new DebugRenderer();

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        add(canvas);
        addKeyListener(input);
        pack();

        //this creates the bufferStrategy for the canvas
        canvas.createBufferStrategy(2);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    //sets the icon and mouse image of the window
    private void setAesthetics(){

        BufferedImage windowIcon = (BufferedImage) ImageLoader.loadImage("/sprites/windowIcon.png");
        setIconImage(windowIcon);

        BufferedImage defaultMouse = (BufferedImage) ImageLoader.loadImage("/sprites/cursor/cursor.png");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Cursor cursor = toolkit.createCustomCursor(defaultMouse, new Point(0, 0), "cursor");
        setCursor(cursor);

    }

    /*
    Renders state assets using a bufferStrategy, which prevents the display from flickering
    JPanels do this by default, I believe. I explained this more fully in my final last
    semester.
     */
    public void render(State state, boolean debugMode){

        BufferStrategy bufferStrategy = canvas.getBufferStrategy();

        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        renderer.render(state, graphics);

        if(debugMode){

            debugRenderer.render(state, graphics);

        }

        //close assets to manage memory
        graphics.dispose();
        //shows the next frame
        bufferStrategy.show();

    }

}
