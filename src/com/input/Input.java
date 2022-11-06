package com.input;

import com.core.Position;

import java.awt.event.*;
/*
This class handles the user input from the keyboard and mouse.

 */
public class Input implements KeyListener, MouseListener, MouseMotionListener {

    private Position mousePosition;
    private boolean mouseClicked;
    private boolean mousePressed;

    private final boolean[] currentlyPressed;
    private final boolean[] pressed;

    public Input() {

        //I have such a high value here because, for some reason, the Windows key has
        //a VK value of 524, but otherwise highest is like two-hundred something.
        currentlyPressed = new boolean[525];
        pressed = new boolean[525];
        mousePosition = new Position(-1, -1);

    }

    //If a key is pressed once (clicked if you will)
    public boolean isPressed(int keycode){

        if(!pressed[keycode] && currentlyPressed[keycode]){

            pressed[keycode] = true;

            return true;

        }

        return false;

    }

    //if a key is held down
    public boolean isCurrentlyPressed(int keyCode){

        return currentlyPressed[keyCode];

    }

    //From here down is simple enough to not comment I think.
    public void clearMouseClicked(){

        mouseClicked = false;

    }

    public Position getMousePosition() {
        return mousePosition;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        currentlyPressed[e.getKeyCode()] = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {

        currentlyPressed[e.getKeyCode()] = false;
        pressed[e.getKeyCode()] = false;

    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {

        mousePressed = true;

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        mouseClicked = true;
        mousePressed = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {

        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());

    }

}
